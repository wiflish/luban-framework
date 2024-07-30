package com.wiflish.luban.framework.pay.core.client.xendit.ewallet;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import lombok.extern.slf4j.Slf4j;

import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.XENDIT_E_WALLET_DANA;

/**
 * Xendit DANA钱包支付
 *
 * @author wiflish
 * @since 2024-07-26
 */
@Slf4j
public class XenditEWalletDANAPayClient extends XenditEWalletAbstractPayClient {
    public XenditEWalletDANAPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, XENDIT_E_WALLET_DANA.getCode(), config);
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();
        channelProperties.setSuccessReturnUrl(reqDTO.getReturnUrl());

        return channelProperties;
    }
}
