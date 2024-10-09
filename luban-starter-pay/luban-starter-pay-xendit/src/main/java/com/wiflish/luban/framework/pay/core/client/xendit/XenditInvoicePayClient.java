package com.wiflish.luban.framework.pay.core.client.xendit;

import com.alibaba.fastjson2.JSON;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import com.xendit.XenditClient;
import com.xendit.model.Invoice;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Xendit支付Client
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Slf4j
public class XenditInvoicePayClient extends XenditPaymentAbstractPayClient {
    private XenditClient xenditClient;

    public XenditInvoicePayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
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
        params.put("amount", getActualPayAmount(reqDTO.getPrice()));
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
}
