package com.wiflish.luban.framework.pay.core.client.xendit.config;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.ewallet.XenditEWalletAstrapayPayClient;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;

/**
 * @author wiflish
 * @since 2024-07-30
 */
public class XenditEWalletAstrapayPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.XENDIT_E_WALLET_ASTRAPAY.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.XENDIT_E_WALLET_ASTRAPAY.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return XenditEWalletAstrapayPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return XenditPayClientConfig.class;
    }
}
