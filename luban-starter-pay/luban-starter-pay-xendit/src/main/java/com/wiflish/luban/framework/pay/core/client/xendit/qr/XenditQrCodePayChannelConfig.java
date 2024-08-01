package com.wiflish.luban.framework.pay.core.client.xendit.qr;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;

/**
 * @author wiflish
 * @since 2024-08-01
 */
public class XenditQrCodePayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.XENDIT_QR_CODE.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.XENDIT_QR_CODE.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return XenditQrCodePayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return XenditPayClientConfig.class;
    }
}
