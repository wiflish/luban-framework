package com.wiflish.luban.framework.pay.core.client.xendit;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.SimulatePayRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.impl.AbstractPayClient;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderStatusRespEnum;
import com.wiflish.luban.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import com.wiflish.luban.framework.pay.xendit.dto.payment.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMATTER;

/**
 * Xendit payment支付，统一的API.
 *
 * @author wiflish
 * @since 2024-07-26
 */
@Slf4j
public abstract class XenditPaymentAbstractPayClient extends AbstractPayClient<XenditPayClientConfig> {
    private static final String XENDIT_PAYMENT_REQUEST_URL = "https://api.xendit.co/payment_requests";
    private static final String XENDIT_REFUND_URL = "https://api.xendit.co/refunds";
    private static final String simulateVirtualAccountUrlTmp = "https://api.xendit.co/v2/payment_methods/%s/payments/simulate";

    protected XenditInvoker xenditInvoker;

    public XenditPaymentAbstractPayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected void doInit() {
        xenditInvoker = SpringUtil.getBean(XenditInvoker.class);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {

        PaymentRequestDTO requestDTO = new PaymentRequestDTO();
        requestDTO.setCurrency(reqDTO.getCurrency())
                .setAmount(reqDTO.getPrice() / 100).setReferenceId(reqDTO.getOutTradeNo())
                .setPaymentMethod(getPaymentMethod(reqDTO))
                .setChannelPropertiesDTO(channelProperties(reqDTO));

        PaymentResponseDTO paymentResponseDTO = null;
        try {
            paymentResponseDTO = xenditInvoker.request(XENDIT_PAYMENT_REQUEST_URL, HttpMethod.POST, config.getApiKey(), requestDTO, PaymentResponseDTO.class);

            return convertPayOrderRespDTO(paymentResponseDTO);
        } catch (HttpClientErrorException e) {
            log.error("发起支付调用失败, 渠道: xendit, req: {}, resp: {}", JSON.toJSONString(requestDTO), JSON.toJSONString(paymentResponseDTO), e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);
            PayOrderRespDTO payOrderRespDTO = PayOrderRespDTO.waitingOf(null, null, reqDTO.getOutTradeNo(), responseBodyAsString);
            payOrderRespDTO.setChannelErrorCode(jsonObject.getString("error_code")).setChannelErrorMsg(jsonObject.getString("message"));

            return payOrderRespDTO;
        }
    }

    protected abstract PaymentMethodDTO getPaymentMethod(PayOrderUnifiedReqDTO reqDTO);

    abstract protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO);


    @Override
    protected PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body) throws Throwable {
        // 1.校验请求合法性
        // 2.幂等处理
        // 3.处理 body
        Map<String, String> bodyObj = HttpUtil.decodeParamMap(body, StandardCharsets.UTF_8);
        Integer status = parseStatus(bodyObj.get("status"));
        Assert.notNull(status, (Supplier<Throwable>) () -> {
            throw new IllegalArgumentException(StrUtil.format("body({}) 的 trade_status 不正确", body));
        });
        return PayOrderRespDTO.of(status, bodyObj.get("external_id"), bodyObj.get("user_id"), parseTime(params.get("paid_at")),
                bodyObj.get("out_trade_no"), body);
    }

    private static Integer parseStatus(String tradeStatus) {
        return Objects.equals("PAID", tradeStatus) ? PayOrderStatusRespEnum.SUCCESS.getStatus()
                : Objects.equals("EXPIRED", tradeStatus) ? PayOrderStatusRespEnum.CLOSED.getStatus() : null;
    }

    protected LocalDateTime parseTime(String str) {
        return LocalDateTimeUtil.parse(str, NORM_DATETIME_FORMATTER);
    }

    @Override
    protected PayOrderRespDTO doGetOrder(String outTradeNo) {
        return null;
    }

    @Override
    protected PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
        PaymentRefundDTO paymentRefundDTO = null;
        PaymentRefundReqDTO refundReqDTO = new PaymentRefundReqDTO();
        refundReqDTO.setReferenceId(reqDTO.getOutRefundNo()).setPaymentRequestId(reqDTO.getChannelOrderNo())
                .setAmount(reqDTO.getRefundPrice() / 100).setCurrency(reqDTO.getCurrency())
                .setMetadata(new HashMap<>());
        try {
            paymentRefundDTO = xenditInvoker.request(XENDIT_REFUND_URL, HttpMethod.POST, config.getApiKey(), refundReqDTO, PaymentRefundDTO.class);
        } catch (HttpClientErrorException e) {
            log.error("发起退款调用失败, 渠道: xenditPayment , req: {}, resp: {}", JSON.toJSONString(refundReqDTO), JSON.toJSONString(paymentRefundDTO), e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);
            PayRefundRespDTO payRefundRespDTO = PayRefundRespDTO.failureOf(reqDTO.getOutRefundNo(), responseBodyAsString);
            payRefundRespDTO.setChannelErrorCode(jsonObject.getString("error_code")).setChannelErrorMsg(jsonObject.getString("message"));
            return payRefundRespDTO;
        }

        return PayRefundRespDTO.waitingOf(paymentRefundDTO.getId(), reqDTO.getOutRefundNo(), paymentRefundDTO);
    }

    @Override
    protected PayRefundRespDTO doParseRefundNotify(Map<String, String> params, String body) throws Throwable {
        return null;
    }

    @Override
    protected PayRefundRespDTO doGetRefund(String outTradeNo, String outRefundNo) throws Throwable {
        return null;
    }

    @Override
    protected PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) throws Throwable {
        return null;
    }

    @Override
    protected PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type) throws Throwable {
        return null;
    }

    @Override
    public SimulatePayRespDTO simulatePayment(String paymentMethodId, Long amount) {
        String url = String.format(simulateVirtualAccountUrlTmp, paymentMethodId);

        Map<String, Object> objMap = new HashMap<>();
        objMap.put("amount", amount / 100);

        return xenditInvoker.request(url, HttpMethod.POST, config.getApiKey(), objMap, SimulatePayRespDTO.class);
    }

    private PayOrderRespDTO convertPayOrderRespDTO(PaymentResponseDTO paymentResponseDTO) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        respDTO.setChannelOrderNo(paymentResponseDTO.getId());
        respDTO.setRawData(paymentResponseDTO);
        if (paymentResponseDTO.getActions() != null && !paymentResponseDTO.getActions().isEmpty()) {
            respDTO.setDisplayMode(PayOrderDisplayModeEnum.IFRAME.getMode());
            for (PaymentActionDTO action : paymentResponseDTO.getActions()) {
                if (action.isMobileUrl()) {
                    return respDTO.setDisplayContent(action.getUrl());
                }
                if (action.isWebUrl()) {
                    return respDTO.setDisplayContent(action.getUrl());
                }
                if (action.isDeepLink()) {
                    return respDTO.setDisplayContent(action.getUrl());
                }
            }
        }
        //处理虚拟账户，虚拟帐户会为每笔交易生成一个临时的账号
        VirtualAccountDTO virtualAccount = paymentResponseDTO.getPaymentMethod().getVirtualAccount();
        if (virtualAccount != null) {
            respDTO.setVirtualAccountNumber(virtualAccount.getChannelProperties().getVirtualAccountNumber());
            respDTO.setVirtualAccountName(config.getAccountName());
        }

        return respDTO;
    }
}
