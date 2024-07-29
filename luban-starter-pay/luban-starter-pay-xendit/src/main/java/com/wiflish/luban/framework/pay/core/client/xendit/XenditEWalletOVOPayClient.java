package com.wiflish.luban.framework.pay.core.client.xendit;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.xendit.enums.XenditConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.XENDIT_E_WALLET_OVO;

/**
 * Xendit OVO钱包支付
 *
 * @author wiflish
 * @since 2024-07-25
 */
@Slf4j
public class XenditEWalletOVOPayClient extends XenditPaymentAbstractPayClient {
    public XenditEWalletOVOPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, XENDIT_E_WALLET_OVO.getCode(), config);
    }

    @Override
    protected Map<String, Object> channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        Map<String, Object> channelProperties = new HashMap<>();
        channelProperties.put(XenditConstant.MOBILE_NUMBER_KEY, StrUtil.addPrefixIfNot(reqDTO.getMobile(), reqDTO.getStatePhonePrefix()));

        return channelProperties;
    }
}
