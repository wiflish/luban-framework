package com.wiflish.luban.framework.pay.core.client.xendit;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.wiflish.luban.framework.pay.xendit.dto.PayoutDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import com.xendit.XenditClient;
import com.xendit.model.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.XENDIT_INVOICE;

/**
 * Xendit支付Client
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Slf4j
public class XenditInvoicePayClient extends XenditPaymentAbstractPayClient {
    private static final String XENDIT_PAYOUT_URL = "https://api.xendit.co/v2/payouts";
    private XenditClient xenditClient;

    public XenditInvoicePayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, XENDIT_INVOICE.getCode(), config);
    }

    @Override
    protected void doInit() {
        super.doInit();
        xenditClient = new XenditClient.Builder().setApikey(config.getApiKey()).build();
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws Throwable {
        Map<String, Object> params = new HashMap<>();
        params.put("external_id", reqDTO.getOutTradeNo());
        params.put("amount", reqDTO.getPrice() / 100);
        params.put("currency", reqDTO.getCurrency());
        params.put("description", reqDTO.getSubject());

        Invoice invoice = xenditClient.invoice.create(params);
        log.info("发起支付, 渠道: xendit , req: {}, resp: {}", JSON.toJSONString(params), JSON.toJSONString(invoice));

        PayOrderRespDTO respDTO = new PayOrderRespDTO();

        respDTO.setChannelOrderNo(invoice.getId());
        respDTO.setDisplayMode(PayOrderDisplayModeEnum.IFRAME.getMode());
        respDTO.setDisplayContent(invoice.getInvoiceUrl());

        return respDTO;
    }

    @Override
    protected PaymentMethodDTO getPaymentMethod(PayOrderUnifiedReqDTO reqDTO) {
        return null;
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        return null;
    }

    @Override
    protected PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) {
        PayoutDTO payoutRespDTO = new PayoutDTO();
        PayoutDTO payoutReqDTO = new PayoutDTO();
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();
        channelProperties.setAccountHolderName(reqDTO.getUserName()).setAccountNumber(reqDTO.getBankAccount());

        payoutReqDTO.setReferenceId(reqDTO.getOutTransferNo()).setChannelCode(reqDTO.getGatewayChannelCode()).setChannelProperties(channelProperties)
                .setAmount(reqDTO.getPrice() / 100).setCurrency(reqDTO.getCurrency());
        try {
            payoutRespDTO = xenditInvoker.request(XENDIT_PAYOUT_URL, HttpMethod.POST, config.getApiKey(), payoutReqDTO, PayoutDTO.class);
        } catch (HttpClientErrorException e) {
            log.error("发起转账调用失败, 渠道: xenditInvoice.payout , req: {}, resp: {}", JSON.toJSONString(payoutReqDTO), JSON.toJSONString(payoutRespDTO), e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);
            PayTransferRespDTO payTransferRespDTO = PayTransferRespDTO.waitingOf(null, reqDTO.getOutTransferNo(), responseBodyAsString);
            payTransferRespDTO.setChannelErrorCode(jsonObject.getString("error_code")).setChannelErrorMsg(jsonObject.getString("message"));
            return payTransferRespDTO;
        }

        return PayTransferRespDTO.dealingOf(payoutRespDTO.getId(), reqDTO.getOutTransferNo(), payoutRespDTO);
    }
}
