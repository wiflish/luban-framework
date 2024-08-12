package com.wiflish.luban.framework.pay.core.client.xendit.ewallet;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * Xendit DANA钱包支付
 *
 * @author wiflish
 * @since 2024-07-26
 */
@Slf4j
public class XenditEWalletDANAPayClient extends XenditEWalletAbstractPayClient {
    public XenditEWalletDANAPayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();
        channelProperties.setSuccessReturnUrl(reqDTO.getReturnUrl());

        return channelProperties;
    }
}
