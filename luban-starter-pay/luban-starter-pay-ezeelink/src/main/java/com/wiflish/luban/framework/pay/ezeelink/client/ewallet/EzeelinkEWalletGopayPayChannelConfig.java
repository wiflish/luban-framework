package com.wiflish.luban.framework.pay.ezeelink.client.ewallet;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;

/**
 * @author wiflish
 * @since 2024-08-09
 */
public class EzeelinkEWalletGopayPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.EZEELINK_E_WALLET_GOPAY.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.EZEELINK_E_WALLET_GOPAY.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return EzeelinkEMoneyPaymentPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return EzeelinkPayClientConfig.class;
    }
}
