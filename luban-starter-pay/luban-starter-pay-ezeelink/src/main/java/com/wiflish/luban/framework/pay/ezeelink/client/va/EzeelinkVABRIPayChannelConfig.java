package com.wiflish.luban.framework.pay.ezeelink.client.va;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;

/**
 * @author wiflish
 * @since 2024-08-12
 */
public class EzeelinkVABRIPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.EZEELINK_VA_BRI.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.EZEELINK_VA_BRI.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return EzeelinkVAPaymentPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return EzeelinkPayClientConfig.class;
    }
}
