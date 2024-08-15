package com.wiflish.luban.framework.pay.config;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClientFactory;
import com.wiflish.luban.framework.pay.core.client.impl.PayClientFactoryImpl;
import com.wiflish.luban.framework.pay.core.client.impl.mock.MockPayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.impl.mock.MockPayClient;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * 支付配置
 *
 * @author wiflish
 * @since 2024-07-22
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "luban.framework.pay", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(PayProperties.class)
public class PayAutoConfiguration {
    @Resource
    private List<PayChannelConfig> payChannelConfigs;

    @Bean
    public PayClientFactory payClientFactory(List<PayChannelConfig> payChannelConfigs) {
        return new PayClientFactoryImpl(payChannelConfigs);
    }

    @Bean
    @Profile(value = {"dev", "test"})
    @ConditionalOnClass(MockPayClient.class)
    public MockPayChannelConfig mockPayChannelConfig() {
        return new MockPayChannelConfig();
    }
}
