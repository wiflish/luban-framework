package com.wiflish.luban.framework.pay.core.client.xendit;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.xendit.enums.XenditConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.XENDIT_E_WALLET_DANA;

/**
 * Xendit DANA钱包支付
 *
 * @author wiflish
 * @since 2024-07-26
 */
@Slf4j
public class XenditEWalletDANAPayClient extends XenditPaymentAbstractPayClient {
    public XenditEWalletDANAPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, XENDIT_E_WALLET_DANA.getCode(), config);
    }

    @Override
    protected Map<String, Object> channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        Map<String, Object> channelProperties = new HashMap<>();
        channelProperties.put(XenditConstant.SUCCESS_REDIRECT_URL_KEY, config.getSuccessUrl());

        return channelProperties;
    }
}
