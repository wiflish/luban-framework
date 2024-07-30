package com.wiflish.luban.framework.pay.core.client.xendit.ewallet;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import lombok.extern.slf4j.Slf4j;

import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.XENDIT_E_WALLET_JENIUSPAY;
import static com.wiflish.luban.framework.pay.xendit.enums.XenditConstant.MOBILE_NUMBER_KEY;

/**
 * Xendit jeniuspay钱包支付
 *
 * @author wiflish
 * @since 2024-07-30
 */
@Slf4j
public class XenditEWalletJeniuspayPayClient extends XenditEWalletAbstractPayClient {
    public XenditEWalletJeniuspayPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, XENDIT_E_WALLET_JENIUSPAY.getCode(), config);
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();
        channelProperties.setCashTag(reqDTO.getChannelExtras().get(MOBILE_NUMBER_KEY));

        return channelProperties;
    }
}
