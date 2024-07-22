package com.wiflish.luban.framework.pay.config;

import com.wiflish.luban.framework.pay.core.client.alipay.*;
import com.wiflish.luban.framework.pay.core.client.alipay.config.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置
 *
 * @author wiflish
 * @since 2024-07-22
 */
@AutoConfiguration
public class AliPayAutoConfiguration {
    @Bean
    @ConditionalOnClass(AliAppPayClient.class)
    public AliAppPayChannelConfig aliAppPayChannelConfig() {
        return new AliAppPayChannelConfig();
    }
    @Bean
    @ConditionalOnClass(AliBarPayClient.class)
    public AliBarPayChannelConfig aliBarPayChannelConfig() {
        return new AliBarPayChannelConfig();
    }
    @Bean
    @ConditionalOnClass(AliPcPayClient.class)
    public AliPcPayChannelConfig aliPcPayChannelConfig() {
        return new AliPcPayChannelConfig();
    }
    @Bean
    @ConditionalOnClass(AliQrPayClient.class)
    public AliQrPayChannelConfig aliQrPayChannelConfig  () {
        return new AliQrPayChannelConfig();
    }
    @Bean
    @ConditionalOnClass(AliWapPayClient.class)
    public AliWapPayChannelConfig aliWapPayChannelConfig  () {
        return new AliWapPayChannelConfig();
    }
}
