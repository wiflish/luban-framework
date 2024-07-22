package com.wiflish.luban.framework.pay.config;

import com.wiflish.luban.framework.pay.core.client.weixin.*;
import com.wiflish.luban.framework.pay.core.client.weixin.config.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * @author wiflish
 * @since 2024-07-22
 */
@AutoConfiguration
public class WxPayAutoConfiguration {

    @Bean
    @ConditionalOnClass(WxAppPayClient.class)
    public WxAppPayChannelConfig wechatAppPayChannelConfig() {
        return new WxAppPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(WxBarPayClient.class)
    public WxBarPayChannelConfig wechatBarPayChannelConfig() {
        return new WxBarPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(WxLitePayClient.class)
    public WxLitePayChannelConfig wechatLitePayChannelConfig() {
        return new WxLitePayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(WxNativePayClient.class)
    public WxNativePayChannelConfig wxNativePayChannelConfig() {
        return new WxNativePayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(WxPubPayClient.class)
    public WxPubPayChannelConfig wxPubPayChannelConfig() {
        return new WxPubPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(WxWapPayClient.class)
    public WxWapPayChannelConfig wxWapPayChannelConfig() {
        return new WxWapPayChannelConfig();
    }
}
