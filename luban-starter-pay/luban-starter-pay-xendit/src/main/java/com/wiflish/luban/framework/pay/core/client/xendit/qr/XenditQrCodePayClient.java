package com.wiflish.luban.framework.pay.core.client.xendit.qr;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPaymentAbstractPayClient;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.QrCodeDTO;
import com.wiflish.luban.framework.pay.xendit.enums.PaymentTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wiflish
 * @since 2024-08-01
 */
@Slf4j
public class XenditQrCodePayClient extends XenditPaymentAbstractPayClient {
    public XenditQrCodePayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected PaymentMethodDTO getPaymentMethod(PayOrderUnifiedReqDTO reqDTO) {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();

        QrCodeDTO qrCodeDTO = new QrCodeDTO();
        qrCodeDTO.setChannelCode(config.getChannelCode())
                .setCurrency(reqDTO.getCurrency()).setAmount(reqDTO.getPrice() / 100)
                .setChannelProperties(channelProperties(reqDTO));

        paymentMethod.setReferenceId(reqDTO.getOutTradeNo())
                .setReusability("ONE_TIME_USE").setType(PaymentTypeEnum.QR_CODE.getName())
                .setQrCode(qrCodeDTO);

        return paymentMethod;
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        return new ChannelPropertiesDTO();
    }
}
