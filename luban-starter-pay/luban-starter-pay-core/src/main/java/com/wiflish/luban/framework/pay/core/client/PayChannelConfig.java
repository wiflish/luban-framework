package com.wiflish.luban.framework.pay.core.client;

/**
 * @author wiflish
 * @since 2024-07-22
 */
public interface PayChannelConfig {
    String getChannelCode();
    String getChannelName();
    Class<? extends PayClient> getPayClient();
    Class<? extends PayClientConfig> getPayClientConfig();
}
