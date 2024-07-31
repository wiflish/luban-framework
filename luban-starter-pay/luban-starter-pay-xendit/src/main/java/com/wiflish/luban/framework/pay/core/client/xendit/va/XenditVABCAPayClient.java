package com.wiflish.luban.framework.pay.core.client.xendit.va;

import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * Xendit VirtualAccount支付，即银行转账
 *
 * @author wiflish
 * @since 2024-07-31
 */
@Slf4j
public class XenditVABCAPayClient extends XenditVAAbstractPayClient {
    public XenditVABCAPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, PayChannelEnum.XENDIT_VA_BCA.getCode(), config);
    }
}
