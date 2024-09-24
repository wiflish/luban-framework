package com.wiflish.luban.framework.pay.ezeelink.client.ewallet;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
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
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkEMoneyReq;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkEMoneyResp;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static com.wiflish.luban.framework.pay.ezeelink.util.EzeelinkUtil.buildBillDescription;

/**
 * Ezeelink OVO钱包支付
 *
 * @author wiflish
 * @since 2024-07-25
 */
@Slf4j
public class EzeelinkEMoneyPaymentPayClient extends EzeelinkAbstractPayClient {
    private EzeelinkInvoker ezeelinkInvoker;

    public EzeelinkEMoneyPaymentPayClient(Long channelId, String channelCode, EzeelinkPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected void doInit() {
        ezeelinkInvoker = SpringUtil.getBean(EzeelinkInvoker.class);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        EzeelinkEMoneyReq requestDTO = new EzeelinkEMoneyReq();
        requestDTO.setEmoneyCode(config.getChannelCode())
                .setBillDescription(buildBillDescription(reqDTO.getSubject()))
                .setPartnerReturnLink(reqDTO.getReturnUrl() == null ? config.getRedirectUrl() : reqDTO.getReturnUrl())
                .setExpirationDuration(config.getExpireMinutes())
                .setTransactionId(reqDTO.getOutTradeNo())
                .setAmount(getActualPayAmount(reqDTO.getPrice()) + "")
                .setPartnerId(config.getPartnerId())
                .setSubPartnerId(config.getSubPartnerId());

        try {
            EzeelinkResp<EzeelinkEMoneyResp> resp = ezeelinkInvoker.request(config.getBaseUrl() + config.getApiUrl(),
                    HttpMethod.POST, config.getApiKey(), config.getApiSecret(), requestDTO, new TypeReference<>() {
                    });
            return convertPayOrderRespDTO(resp);
        } catch (HttpClientErrorException e) {
            log.error("发起支付调用失败, 渠道: ezeelink", e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);
            PayOrderRespDTO payOrderRespDTO = PayOrderRespDTO.waitingOf(null, null, reqDTO.getOutTradeNo(), responseBodyAsString);
            payOrderRespDTO.setChannelErrorCode(jsonObject.getString("error_code")).setChannelErrorMsg(jsonObject.getString("error_message"));

            return payOrderRespDTO;
        }
    }

    private PayOrderRespDTO convertPayOrderRespDTO(EzeelinkResp<EzeelinkEMoneyResp> resp) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        respDTO.setChannelOrderNo(resp.getResult().getTransactionCode());
        respDTO.setRawData(resp)
                .setDisplayMode(PayOrderDisplayModeEnum.IFRAME.getMode())
                .setDisplayContent(resp.getResult().getPaymentLink());

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
    protected PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type) throws Throwable {
        return null;
    }
}
