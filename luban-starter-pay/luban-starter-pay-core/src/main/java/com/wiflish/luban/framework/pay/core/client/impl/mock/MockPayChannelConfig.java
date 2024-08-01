package com.wiflish.luban.framework.pay.core.client.impl.mock;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.client.impl.NonePayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;

/**
 * @author wiflish
 * @since 2024-08-01
 */
public class MockPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.MOCK.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.MOCK.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return MockPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return NonePayClientConfig.class;
    }
}
