package com.wiflish.luban.framework.pay.core.client.weixin.config;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.core.client.weixin.WxLitePayClient;
import com.wiflish.luban.framework.pay.core.client.weixin.WxPayClientConfig;
import com.wiflish.luban.framework.pay.core.client.weixin.WxPubPayClient;

/**
 * @author wiflish
 * @since 2024-07-22
 */
public class WxPubPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.WX_PUB.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.WX_PUB.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return WxPubPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return WxPayClientConfig.class;
    }
}
