package com.wiflish.luban.framework.pay.ezeelink.client.ewallet;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wiflish.luban.framework.common.exception.ServiceException;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.impl.AbstractPayClient;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.wiflish.luban.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkInvoker;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkBaseDTO;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkOVOReq;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkOVOResp;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkResp;
import com.wiflish.luban.framework.pay.ezeelink.enums.EzeelinkOVOLookupAccountStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static com.wiflish.luban.framework.common.exception.enums.GlobalErrorCodeConstants.INVOKER_ERROR;
import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.EZEELINK_E_WALLET_OVO;

/**
 * Ezeelink OVO钱包支付
 *
 * @author wiflish
 * @since 2024-08-08
 */
@Slf4j
public class EzeelinkEWalletOVOPayClient extends AbstractPayClient<EzeelinkPayClientConfig> {
    private static final String bindingAccountApi = "/ezpaygateway/api/v1/ovo/payment/bindaccount";
    private static final String lookupAccountApi = "/ezpaygateway/api/v1/ovo/payment/lookupaccount";
    private EzeelinkInvoker ezeelinkInvoker;

    public EzeelinkEWalletOVOPayClient(Long channelId, EzeelinkPayClientConfig config) {
        super(channelId, EZEELINK_E_WALLET_OVO.getCode(), config);
    }

    @Override
    protected void doInit() {
        ezeelinkInvoker = SpringUtil.getBean(EzeelinkInvoker.class);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        EzeelinkOVOReq requestDTO = new EzeelinkOVOReq();
        requestDTO.setPhoneNumber(getPhoneNumber(reqDTO))
                .setPartnerId(config.getPartnerId())
                .setSubPartnerId(config.getSubPartnerId());

        //先检查是否已绑定。
        EzeelinkResp<EzeelinkBaseDTO> lookup = ezeelinkInvoker.request(config.getBaseUrl() + lookupAccountApi, HttpMethod.POST,
                config.getApiKey(), config.getApiSecret(), requestDTO, new TypeReference<>() {
                });

        requestDTO.setRedirectUrl(reqDTO.getReturnUrl() == null ? config.getRedirectUrl() : reqDTO.getReturnUrl());
        // 需要绑定.
        if (EzeelinkOVOLookupAccountStatusEnum.isOpen(lookup.getResult().getStatus())) {
            EzeelinkResp<EzeelinkOVOResp> bindAccount = ezeelinkInvoker.request(config.getBaseUrl() + bindingAccountApi, HttpMethod.POST, config.getApiKey(), config.getApiSecret(), requestDTO, new TypeReference<>() {
            });

            return convertPayOrderRespDTO(bindAccount, PayOrderDisplayModeEnum.URL);
        }
        if (EzeelinkOVOLookupAccountStatusEnum.isProcessing(lookup.getResult().getStatus())) {
            throw new ServiceException(INVOKER_ERROR.getCode(), EzeelinkOVOLookupAccountStatusEnum.PROCESSING.getDesc());
        }
        if (EzeelinkOVOLookupAccountStatusEnum.isUnusable(lookup.getResult().getStatus())) {
            throw new ServiceException(INVOKER_ERROR.getCode(), EzeelinkOVOLookupAccountStatusEnum.UNUSABLE.getDesc());
        }

        // 进行支付
        EzeelinkResp<EzeelinkOVOResp> resp = null;
        try {
            requestDTO.setCashAmount((reqDTO.getPrice() / 100) + "").setTransactionCode(reqDTO.getOutTradeNo());
            resp = ezeelinkInvoker.request(config.getBaseUrl() + config.getApiUrl(), HttpMethod.POST, config.getApiKey(), config.getApiSecret(), requestDTO, new TypeReference<>() {
            });

            return convertPayOrderRespDTO(resp, null);
        } catch (HttpClientErrorException e) {
            log.error("发起支付调用失败, 渠道: ezeelink, req: {}, resp: {}", JSON.toJSONString(requestDTO), JSON.toJSONString(resp), e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);
            PayOrderRespDTO payOrderRespDTO = PayOrderRespDTO.waitingOf(null, null, reqDTO.getOutTradeNo(), responseBodyAsString);
            payOrderRespDTO.setChannelErrorCode(jsonObject.getString("error_code")).setChannelErrorMsg(jsonObject.getString("error_message"));

            return payOrderRespDTO;
        }
    }

    private static String getPhoneNumber(PayOrderUnifiedReqDTO reqDTO) {
        if (reqDTO.getMobile().indexOf("0") == 0) {
            return reqDTO.getMobile();
        }
        return "0" + reqDTO.getMobile();
    }

    private PayOrderRespDTO convertPayOrderRespDTO(EzeelinkResp<EzeelinkOVOResp> resp, PayOrderDisplayModeEnum modeEnum) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        respDTO.setChannelOrderNo(resp.getResult().getTransactionCode()); // ovo支付没有id， 这个transactionCode就是pay_order_extension的no.
        respDTO.setRawData(resp)
                .setDisplayMode(modeEnum == null ? PayOrderDisplayModeEnum.IFRAME.getMode() : modeEnum.getMode())
                .setDisplayContent(resp.getResult().getWebviewLink());

        return respDTO;
    }

    @Override
    protected PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body) throws Throwable {
        return null;
    }

    @Override
    protected PayOrderRespDTO doGetOrder(String outTradeNo) throws Throwable {
        return null;
    }

    @Override
    protected PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) throws Throwable {
        return null;
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
}
