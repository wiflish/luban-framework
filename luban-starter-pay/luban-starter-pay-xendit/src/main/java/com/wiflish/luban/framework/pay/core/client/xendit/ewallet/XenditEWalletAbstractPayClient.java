package com.wiflish.luban.framework.pay.core.client.xendit.ewallet;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPaymentAbstractPayClient;
import com.wiflish.luban.framework.pay.xendit.dto.payment.EWalletDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import com.wiflish.luban.framework.pay.xendit.enums.PaymentTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * Xendit OVO钱包支付
 *
 * @author wiflish
 * @since 2024-07-25
 */
@Slf4j
public abstract class XenditEWalletAbstractPayClient extends XenditPaymentAbstractPayClient {
    public XenditEWalletAbstractPayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
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
}
