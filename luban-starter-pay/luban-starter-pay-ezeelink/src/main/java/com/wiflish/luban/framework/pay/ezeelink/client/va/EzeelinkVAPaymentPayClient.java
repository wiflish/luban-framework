package com.wiflish.luban.framework.pay.ezeelink.client.va;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.impl.AbstractPayClient;
import com.wiflish.luban.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkInvoker;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkResp;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkVAReq;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkVAResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static com.wiflish.luban.framework.pay.ezeelink.util.EzeelinkUtil.buildBillDescription;

/**
 * Ezeelink Virtual Account支付
 * 014 BCA (Bank Central Asia)
 * 008 Bank Mandiri
 * 002 BRI
 * 009 BNI
 * 011 Danamon
 * 013 PERMATA
 * 451 Bank Syariah Indonesia
 *
 * @author wiflish
 * @since 2024-08-08
 */
@Slf4j
public class EzeelinkVAPaymentPayClient extends AbstractPayClient<EzeelinkPayClientConfig> {
    private EzeelinkInvoker ezeelinkInvoker;

    public EzeelinkVAPaymentPayClient(Long channelId, String channelCode, EzeelinkPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected void doInit() {
        ezeelinkInvoker = SpringUtil.getBean(EzeelinkInvoker.class);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws Throwable {
        EzeelinkVAReq requestDTO = new EzeelinkVAReq();
        requestDTO.setBankCode(config.getChannelCode())
                .setBillDescription(buildBillDescription(reqDTO.getSubject()))
                .setExpirationDuration(minute2Hour(config.getExpireMinutes()))
                .setTransactionId(reqDTO.getOutTradeNo())
                .setPartnerId(config.getPartnerId())
                .setAmount(getActualPayAmount(reqDTO.getPrice()) + "")
                .setSubPartnerId(config.getSubPartnerId());

        EzeelinkResp<List<EzeelinkVAResp>> resp = null;
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

    @Override
    public PayOrderRespDTO parseVirtualAccountResponseData(String payResponseData) {
        EzeelinkResp<List<EzeelinkVAResp>> resp = JSON.parseObject(payResponseData, new TypeReference<>() {
        });

        PayOrderRespDTO payOrderRespDTO = new PayOrderRespDTO();
        payOrderRespDTO.setVirtualAccountName(resp.getResult().getFirst().getChannelName());
        payOrderRespDTO.setVirtualAccountNumber(resp.getResult().getFirst().getVaNumber());

        return payOrderRespDTO;
    }

    private String minute2Hour(Integer expireMinutes) {
        return expireMinutes / 60.0 + "";
    }

    private PayOrderRespDTO convertPayOrderRespDTO(EzeelinkResp<List<EzeelinkVAResp>> resp) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        EzeelinkVAResp vaResp = resp.getResult().getFirst();
        respDTO.setChannelOrderNo(vaResp.getTransactionCode());
        respDTO.setRawData(resp)
                .setVirtualAccountName(vaResp.getChannelName())
                .setVirtualAccountNumber(vaResp.getVaNumber());

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
