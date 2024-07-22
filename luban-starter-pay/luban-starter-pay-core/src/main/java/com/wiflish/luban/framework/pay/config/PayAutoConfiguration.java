package com.wiflish.luban.framework.pay.config;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClientFactory;
import com.wiflish.luban.framework.pay.core.client.impl.PayClientFactoryImpl;
import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 支付配置
 *
 * @author wiflish
 * @since 2024-07-22
 */
@AutoConfiguration
public class PayAutoConfiguration {
    @Resource
    private List<PayChannelConfig> payChannelConfigs;

    @Bean
    public PayClientFactory payClientFactory(List<PayChannelConfig> payChannelConfigs) {
        return new PayClientFactoryImpl(payChannelConfigs);
    }

}
