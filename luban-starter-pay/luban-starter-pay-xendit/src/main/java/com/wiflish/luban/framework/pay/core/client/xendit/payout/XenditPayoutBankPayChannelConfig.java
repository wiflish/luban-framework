package com.wiflish.luban.framework.pay.core.client.xendit.payout;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;

/**
 * @author wiflish
 * @since 2024-07-22
 */
public class XenditPayoutBankPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.XENDIT_PAYOUT_BANK.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.XENDIT_PAYOUT_BANK.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return XenditPayoutBankPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return XenditPayClientConfig.class;
    }
}
