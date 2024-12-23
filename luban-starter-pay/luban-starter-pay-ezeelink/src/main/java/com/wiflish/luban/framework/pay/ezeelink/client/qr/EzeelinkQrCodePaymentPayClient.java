package com.wiflish.luban.framework.pay.ezeelink.client.qr;

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
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkQrCodeReq;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkQrCodeResp;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static com.wiflish.luban.framework.pay.ezeelink.util.EzeelinkUtil.buildBillDescription;

/**
 * Ezeelink QrCode 支付
 *
 * @author wiflish
 * @since 2024-08-08
 */
@Slf4j
public class EzeelinkQrCodePaymentPayClient extends EzeelinkAbstractPayClient {
    public EzeelinkQrCodePaymentPayClient(Long channelId, String channelCode, EzeelinkPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws Throwable {
        EzeelinkQrCodeReq requestDTO = new EzeelinkQrCodeReq();
        requestDTO.setExpiryTime(config.getExpireMinutes())
                .setCurrency(reqDTO.getCurrency())
                .setStoreExtId(config.getStoreExtId())
                .setTerminalId(config.getTerminalId())
                .setBillDescription(buildBillDescription(reqDTO.getSubject()))
                .setTransactionId(reqDTO.getOutTradeNo())
                .setAmount(getActualPayAmount(reqDTO.getPrice()) + "")
                .setPartnerId(config.getPartnerId())
                .setSubPartnerId(config.getSubPartnerId());

        EzeelinkResp<EzeelinkQrCodeResp> resp = null;
        try {
            resp = ezeelinkInvoker.request(config.getBaseUrl() + config.getApiUrl(), HttpMethod.POST, config.getApiKey(), config.getApiSecret(), requestDTO, new TypeReference<>() {
            });

            return convertPayOrderRespDTO(resp);
        } catch (HttpClientErrorException e) {
            log.error("发起支付调用失败, 渠道: ezeelink, req: {}, resp: {}", JSON.toJSONString(requestDTO), JSON.toJSONString(resp), e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);
            PayOrderRespDTO payOrderRespDTO = PayOrderRespDTO.waitingOf(null, null, reqDTO.getOutTradeNo(), responseBodyAsString);
            payOrderRespDTO.setChannelErrorCode(jsonObject.getString("error_code")).setChannelErrorMsg(jsonObject.getString("error_message"));

            return payOrderRespDTO;
        }
    }

    private PayOrderRespDTO convertPayOrderRespDTO(EzeelinkResp<EzeelinkQrCodeResp> resp) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        EzeelinkQrCodeResp vaResp = resp.getResult();
        respDTO.setChannelOrderNo(vaResp.getTransactionCode());
        respDTO.setRawData(resp)
                .setDisplayMode(PayOrderDisplayModeEnum.QR_CODE_URL.getMode())
                .setDisplayContent(vaResp.getQrUrl());

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
