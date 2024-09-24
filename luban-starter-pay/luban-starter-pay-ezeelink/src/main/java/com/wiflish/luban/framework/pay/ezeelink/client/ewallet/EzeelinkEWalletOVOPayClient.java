package com.wiflish.luban.framework.pay.ezeelink.client.ewallet;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wiflish.luban.framework.common.exception.ServiceException;
import com.wiflish.luban.framework.common.util.string.PhoneUtils;
import com.wiflish.luban.framework.i18n.config.I18nProperties;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.wiflish.luban.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkAbstractPayClient;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkInvoker;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;
import com.wiflish.luban.framework.pay.ezeelink.dto.*;
import com.wiflish.luban.framework.pay.ezeelink.enums.EzeelinkOVOLookupAccountStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static com.wiflish.luban.framework.common.exception.enums.GlobalErrorCodeConstants.INVOKER_ERROR;
import static com.wiflish.luban.framework.pay.ezeelink.enums.EzeelinkApiEnum.*;

/**
 * Ezeelink OVO钱包支付
 *
 * @author wiflish
 * @since 2024-08-08
 */
@Slf4j
public class EzeelinkEWalletOVOPayClient extends EzeelinkAbstractPayClient {
    private EzeelinkInvoker ezeelinkInvoker;
    private I18nProperties i18nProperties;

    public EzeelinkEWalletOVOPayClient(Long channelId, String channelCode, EzeelinkPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected void doInit() {
        ezeelinkInvoker = SpringUtil.getBean(EzeelinkInvoker.class);
        i18nProperties = SpringUtil.getBean(I18nProperties.class);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        EzeelinkOVOReq requestDTO = new EzeelinkOVOReq();
        requestDTO.setPhoneNumber(PhoneUtils.addInternalPhonePrefix(reqDTO.getMobile(), i18nProperties.getStatePhonePrefix()))
                .setPartnerId(config.getPartnerId())
                .setSubPartnerId(config.getSubPartnerId());

        //先检查是否已绑定。
        EzeelinkResp<EzeelinkBaseDTO> lookup = ezeelinkInvoker.request(config.getBaseUrl() + PAYMENT_API_ACCOUNT_LOOKUP.getApi(), HttpMethod.POST,
                config.getApiKey(), config.getApiSecret(), requestDTO, new TypeReference<>() {
                });

        requestDTO.setRedirectUrl(reqDTO.getReturnUrl() == null ? config.getRedirectUrl() : reqDTO.getReturnUrl());
        // 需要绑定.
        if (EzeelinkOVOLookupAccountStatusEnum.isOpen(lookup.getResult().getStatus())) {
            EzeelinkResp<EzeelinkOVOResp> bindAccount = ezeelinkInvoker.request(config.getBaseUrl() + PAYMENT_API_ACCOUNT_BIND.getApi(), HttpMethod.POST, config.getApiKey(), config.getApiSecret(), requestDTO, new TypeReference<>() {
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
            requestDTO.setCashAmount(getActualPayAmount(reqDTO.getPrice()) + "").setTransactionCode(reqDTO.getOutTradeNo());
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
        EzeelinkAccountDTO accountDTO = new EzeelinkAccountDTO();
        accountDTO.setCustomerPhoneNumber(reqDTO.getMobile())
                .setEwalletCode(config.getChannelCode()).setTransferAmount(getActualPayAmount(reqDTO.getRefundPrice()) + "")
                .setPartnerTransId(reqDTO.getOutRefundNo());

        EzeelinkEMoneyTransDTO transReq = new EzeelinkEMoneyTransDTO();
        transReq.setAccounts(List.of(accountDTO)).setPartnerId(config.getPartnerId()).setSubPartnerId(config.getSubPartnerId());

        // 发起退款
        try {
            EzeelinkResp<EzeelinkEMoneyTransDTO> resp = ezeelinkInvoker.request(config.getBaseUrl() + TRANSFER_API_EMONEY.getApi(), HttpMethod.POST, config.getApiKey(), config.getApiSecret(), transReq, new TypeReference<>() {
            });

            return PayRefundRespDTO.waitingOf(resp.getResult().getTransactionCode(), reqDTO.getOutRefundNo(), resp);
        } catch (HttpClientErrorException e) {
            log.error("发起退款调用失败, 渠道: ezeelink , req: {} ", JSON.toJSONString(transReq), e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);
            PayRefundRespDTO payRefundRespDTO = PayRefundRespDTO.failureOf(reqDTO.getOutRefundNo(), responseBodyAsString);
            payRefundRespDTO.setChannelErrorCode(jsonObject.getString("error_code")).setChannelErrorMsg(jsonObject.getString("error_message"));

            return payRefundRespDTO;
        }
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
    protected PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type) throws Throwable {
        return null;
    }
}
