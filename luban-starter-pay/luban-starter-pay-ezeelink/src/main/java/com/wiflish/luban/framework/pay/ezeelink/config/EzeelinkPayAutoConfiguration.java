package com.wiflish.luban.framework.pay.ezeelink.config;

import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkInvoker;
import com.wiflish.luban.framework.pay.ezeelink.client.ewallet.*;
import com.wiflish.luban.framework.pay.ezeelink.client.qr.EzeelinkQrCodePayChannelConfig;
import com.wiflish.luban.framework.pay.ezeelink.client.qr.EzeelinkQrCodePaymentPayClient;
import com.wiflish.luban.framework.pay.ezeelink.client.va.EzeelinkVABCAPayChannelConfig;
import com.wiflish.luban.framework.pay.ezeelink.client.va.EzeelinkVAMandiriPayChannelConfig;
import com.wiflish.luban.framework.pay.ezeelink.client.va.EzeelinkVAPaymentPayClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@AutoConfiguration
public class EzeelinkPayAutoConfiguration {
    @Bean
    public EzeelinkInvoker ezeelinkInvoker(RestTemplate restTemplate) {
        return new EzeelinkInvoker(restTemplate);
    }

    @Bean
    @ConditionalOnClass(EzeelinkEWalletOVOPayClient.class)
    public EzeelinkEWalletOVOPayChannelConfig ezeelinkEWalletOVOPayChannelConfig() {
        return new EzeelinkEWalletOVOPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(EzeelinkEMoneyPaymentPayClient.class)
    public EzeelinkEWalletDANAPayChannelConfig ezeelinkEWalletDANAPayChannelConfig() {
        return new EzeelinkEWalletDANAPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(EzeelinkEMoneyPaymentPayClient.class)
    public EzeelinkEWalletGopayPayChannelConfig ezeelinkEWalletGopayPayChannelConfig() {
        return new EzeelinkEWalletGopayPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(EzeelinkVAPaymentPayClient.class)
    public EzeelinkVABCAPayChannelConfig ezeelinkVABCAPayChannelConfig() {
        return new EzeelinkVABCAPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(EzeelinkVAPaymentPayClient.class)
    public EzeelinkVAMandiriPayChannelConfig ezeelinkVAMandiriPayChannelConfig() {
        return new EzeelinkVAMandiriPayChannelConfig();
    }

    @Bean
    @ConditionalOnClass(EzeelinkQrCodePaymentPayClient.class)
    public EzeelinkQrCodePayChannelConfig ezeelinkQrCodePayChannelConfig() {
        return new EzeelinkQrCodePayChannelConfig();
    }
}
