package com.wiflish.luban.framework.pay.ezeelink.client.qr;

import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;

/**
 * @author wiflish
 * @since 2024-08-08
 */
public class EzeelinkQrCodePayChannelConfig implements PayChannelConfig {
    @Override
    public String getChannelCode() {
        return PayChannelEnum.EZEELINK_QR_CODE.getCode();
    }

    @Override
    public String getChannelName() {
        return PayChannelEnum.EZEELINK_QR_CODE.getName();
    }

    @Override
    public Class<? extends PayClient> getPayClient() {
        return EzeelinkQrCodePaymentPayClient.class;
    }

    @Override
    public Class<? extends PayClientConfig> getPayClientConfig() {
        return EzeelinkPayClientConfig.class;
    }
}
