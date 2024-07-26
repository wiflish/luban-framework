package com.wiflish.luban.framework.pay.core.client.xendit.config;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditEWalletDANAPayClient;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;

/**
 * @author wiflish
 * @since 2024-07-26
 */
public class XenditEWalletDANAPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.XENDIT_E_WALLET_DANA.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.XENDIT_E_WALLET_DANA.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return XenditEWalletDANAPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return XenditPayClientConfig.class;
    }
}
