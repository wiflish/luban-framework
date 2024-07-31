package com.wiflish.luban.framework.pay.config;

import com.wiflish.luban.framework.pay.core.client.xendit.XenditInvoicePayClient;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditInvoker;
import com.wiflish.luban.framework.pay.core.client.xendit.card.XenditCardPayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.card.XenditCardPayClient;
import com.wiflish.luban.framework.pay.core.client.xendit.config.*;
import com.wiflish.luban.framework.pay.core.client.xendit.ewallet.*;
import com.wiflish.luban.framework.pay.core.client.xendit.va.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author wiflish
 * @since 2024-07-22
 */
@AutoConfiguration
public class XenditPayAutoConfiguration {
    @Bean
    public XenditInvoker xenditInvoker(RestTemplate restTemplate) {
        return new XenditInvoker(restTemplate);
    }

    @Bean
    @ConditionalOnClass(XenditInvoicePayClient.class)
    public XenditInvoicePayChannelConfig xenditInvoicePayChannelConfig() {
        return new XenditInvoicePayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditCardPayClient.class)
    public XenditCardPayChannelConfig xenditCardPayChannelConfig() {
        return new XenditCardPayChannelConfig();
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

    @Bean
    @ConditionalOnClass(XenditEWalletLinkAjaPayClient.class)
    public XenditEWalletLinkAjaPayChannelConfig xenditEWalletLinkAjaPayChannelConfig() {
        return new XenditEWalletLinkAjaPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditEWalletAstrapayPayClient.class)
    public XenditEWalletAstrapayPayChannelConfig xenditEWalletAstrapayPayChannelConfig() {
        return new XenditEWalletAstrapayPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditEWalletJeniuspayPayClient.class)
    public XenditEWalletJeniuspayPayChannelConfig xenditEWalletJeniuspayPayChannelConfig() {
        return new XenditEWalletJeniuspayPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditEWalletShopeepayPayClient.class)
    public XenditEWalletShopeepayPayChannelConfig xenditEWalletShopeepayPayChannelConfig() {
        return new XenditEWalletShopeepayPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVABCAPayClient.class)
    public XenditVABCAPayChannelConfig xenditVABCAPayChannelConfig() {
        return new XenditVABCAPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVABSIPayClient.class)
    public XenditVABSIPayChannelConfig xenditVABSIPayChannelConfig() {
        return new XenditVABSIPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVABJBPayClient.class)
    public XenditVABJBPayChannelConfig xenditVABJBPayChannelConfig() {
        return new XenditVABJBPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVACIMBPayClient.class)
    public XenditVACIMBPayChannelConfig xenditVACIMBPayChannelConfig() {
        return new XenditVACIMBPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVASAHABATPayClient.class)
    public XenditVASAHABATPayChannelConfig xenditVASAHABATPayChannelConfig() {
        return new XenditVASAHABATPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVAARTAJASAPayClient.class)
    public XenditVAARTAJASAPayChannelConfig xenditVAARTAJASAPayChannelConfig() {
        return new XenditVAARTAJASAPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVABRIPayClient.class)
    public XenditVABRIPayChannelConfig xenditVABRIPayChannelConfig() {
        return new XenditVABRIPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVABNIPayClient.class)
    public XenditVABNIPayChannelConfig xenditVABNIPayChannelConfig() {
        return new XenditVABNIPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVAMANDIRIPayClient.class)
    public XenditVAMANDIRIPayChannelConfig xenditVAMANDIRIPayChannelConfig() {
        return new XenditVAMANDIRIPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(XenditVAPERMATAPayClient.class)
    public XenditVAPERMATAPayChannelConfig xenditVAPERMATAPayChannelConfig() {
        return new XenditVAPERMATAPayChannelConfig();
    }
}
