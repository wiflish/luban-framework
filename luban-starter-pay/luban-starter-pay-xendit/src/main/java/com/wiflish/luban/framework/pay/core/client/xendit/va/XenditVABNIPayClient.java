package com.wiflish.luban.framework.pay.core.client.xendit.va;

import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wiflish
 * @since 2024-07-31
 */
@Slf4j
public class XenditVABNIPayClient extends XenditVAAbstractPayClient {
    public XenditVABNIPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, PayChannelEnum.XENDIT_VA_BNI.getCode(), config);
    }
}
