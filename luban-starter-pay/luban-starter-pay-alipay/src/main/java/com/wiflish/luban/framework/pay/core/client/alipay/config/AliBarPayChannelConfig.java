package com.wiflish.luban.framework.pay.core.client.alipay.config;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.client.alipay.AliBarPayClient;
import com.wiflish.luban.framework.pay.core.client.alipay.AliPayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;

/**
 * @author wiflish
 * @since 2024-07-22
 */
public class AliBarPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.ALIPAY_BAR.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.ALIPAY_BAR.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return AliBarPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return AliPayClientConfig.class;
    }
}
