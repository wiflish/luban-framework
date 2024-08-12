package com.wiflish.luban.framework.pay.core.client.xendit.ewallet;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * Xendit OVO钱包支付
 *
 * @author wiflish
 * @since 2024-07-25
 */
@Slf4j
public class XenditEWalletOVOPayClient extends XenditEWalletAbstractPayClient {
    public XenditEWalletOVOPayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();
        channelProperties.setMobileNumber(StrUtil.addPrefixIfNot(reqDTO.getMobile(), reqDTO.getStatePhonePrefix()));

        return channelProperties;
    }
}
