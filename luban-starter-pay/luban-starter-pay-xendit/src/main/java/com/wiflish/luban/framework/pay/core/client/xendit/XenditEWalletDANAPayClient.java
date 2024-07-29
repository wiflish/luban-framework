package com.wiflish.luban.framework.pay.core.client.xendit;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.EWalletDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import com.wiflish.luban.framework.pay.xendit.enums.PaymentTypeEnum;
import lombok.extern.slf4j.Slf4j;

import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.XENDIT_E_WALLET_DANA;

/**
 * Xendit DANA钱包支付
 *
 * @author wiflish
 * @since 2024-07-26
 */
@Slf4j
public class XenditEWalletDANAPayClient extends XenditPaymentAbstractPayClient {
    public XenditEWalletDANAPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, XENDIT_E_WALLET_DANA.getCode(), config);
    }

    @Override
    protected PaymentMethodDTO getPaymentMethod(PayOrderUnifiedReqDTO reqDTO) {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();

        EWalletDTO eWalletDTO = new EWalletDTO();
        eWalletDTO.setChannelCode(config.getChannelCode()).setChannelProperties(channelProperties(reqDTO));
        paymentMethod.setReferenceId(reqDTO.getOutTradeNo())
                .setReusability("ONE_TIME_USE").setType(PaymentTypeEnum.EWALLET.getName())
                .setEwallet(eWalletDTO);

        return paymentMethod;
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();
        channelProperties.setSuccessReturnUrl(config.getSuccessUrl());

        return channelProperties;
    }
}
