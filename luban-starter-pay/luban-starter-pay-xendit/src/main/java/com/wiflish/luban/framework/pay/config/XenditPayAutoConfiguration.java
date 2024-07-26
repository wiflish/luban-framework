package com.wiflish.luban.framework.pay.config;

import com.wiflish.luban.framework.pay.core.client.xendit.XenditEWalletDANAPayClient;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditEWalletOVOPayClient;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditInvoicePayClient;
import com.wiflish.luban.framework.pay.core.client.xendit.config.XenditEWalletDANAPayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.config.XenditEWalletOVOPayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.config.XenditInvoicePayChannelConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * @author wiflish
 * @since 2024-07-22
 */
@AutoConfiguration
public class XenditPayAutoConfiguration {
    @Bean
    @ConditionalOnClass(XenditInvoicePayClient.class)
    public XenditInvoicePayChannelConfig xenditInvoicePayChannelConfig() {
        return new XenditInvoicePayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditEWalletOVOPayClient.class)
    public XenditEWalletOVOPayChannelConfig xenditEWalletOVOPayChannelConfig() {
        return new XenditEWalletOVOPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditEWalletDANAPayClient.class)
    public XenditEWalletDANAPayChannelConfig xenditEWalletDANAPayChannelConfig() {
        return new XenditEWalletDANAPayChannelConfig();
    }
}
