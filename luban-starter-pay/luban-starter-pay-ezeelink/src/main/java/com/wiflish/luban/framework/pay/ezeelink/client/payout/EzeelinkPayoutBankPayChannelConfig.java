package com.wiflish.luban.framework.pay.ezeelink.client.payout;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;

/**
 * ezeelink 转账到银行渠道配置。
 *
 * @author wiflish
 * @since 2024-10-04
 */
public class EzeelinkPayoutBankPayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.EZEELINK_PAYOUT_BANK.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.EZEELINK_PAYOUT_BANK.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return EzeelinkPayoutBankPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return EzeelinkPayClientConfig.class;
    }
}
