package com.wiflish.luban.framework.pay.core.client.weixin.config;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.core.client.weixin.WxAppPayClient;
import com.wiflish.luban.framework.pay.core.client.weixin.WxPayClientConfig;

/**
 * @author wiflish
 * @since 2024-07-22
 */
public class WxAppPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.WX_APP.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.WX_APP.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return WxAppPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return WxPayClientConfig.class;
    }
}
