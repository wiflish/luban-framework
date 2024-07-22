package com.wiflish.luban.framework.pay.core.client.weixin.config;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.core.client.weixin.WxLitePayClient;
import com.wiflish.luban.framework.pay.core.client.weixin.WxPayClientConfig;

/**
 * @author wiflish
 * @since 2024-07-22
 */
public class WxLitePayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.WX_LITE.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.WX_LITE.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return WxLitePayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return WxPayClientConfig.class;
    }
}
