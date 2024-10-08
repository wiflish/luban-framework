package com.wiflish.luban.framework.pay.core.client.xendit.payout;

import com.wiflish.luban.framework.pay.core.client.xendit.XenditInvoicePayClient;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Xendit支付Client
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Slf4j
public class XenditPayoutBankPayClient extends XenditInvoicePayClient {

    public XenditPayoutBankPayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }
}
