package com.wiflish.luban.framework.pay.core.client.xendit;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.EWalletDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import com.wiflish.luban.framework.pay.xendit.enums.PaymentTypeEnum;
import lombok.extern.slf4j.Slf4j;

import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.XENDIT_E_WALLET_OVO;

/**
 * Xendit OVO钱包支付
 *
 * @author wiflish
 * @since 2024-07-25
 */
@Slf4j
public class XenditEWalletOVOPayClient extends XenditPaymentAbstractPayClient {
    public XenditEWalletOVOPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, XENDIT_E_WALLET_OVO.getCode(), config);
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
        channelProperties.setMobileNumber(StrUtil.addPrefixIfNot(reqDTO.getMobile(), reqDTO.getStatePhonePrefix()));

        return channelProperties;
    }
}
