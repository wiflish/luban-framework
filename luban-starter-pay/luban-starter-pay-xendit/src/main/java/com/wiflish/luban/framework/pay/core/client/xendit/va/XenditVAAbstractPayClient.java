package com.wiflish.luban.framework.pay.core.client.xendit.va;

import com.wiflish.luban.framework.common.util.date.LocalDateTimeUtils;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPaymentAbstractPayClient;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.VirtualAccountDTO;
import com.wiflish.luban.framework.pay.xendit.enums.PaymentTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Xendit VirtualAccount支付，即银行转账
 *
 * @author wiflish
 * @since 2024-07-31
 */
@Slf4j
public abstract class XenditVAAbstractPayClient extends XenditPaymentAbstractPayClient {
    public XenditVAAbstractPayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected PaymentMethodDTO getPaymentMethod(PayOrderUnifiedReqDTO reqDTO) {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();

        VirtualAccountDTO virtualAccountDTO = new VirtualAccountDTO();
        virtualAccountDTO.setChannelCode(config.getChannelCode())
                .setCurrency(reqDTO.getCurrency()).setAmount(reqDTO.getPrice() / 100)
                .setChannelProperties(channelProperties(reqDTO));

        paymentMethod.setReferenceId(reqDTO.getOutTradeNo())
                .setReusability("ONE_TIME_USE").setType(PaymentTypeEnum.VIRTUAL_ACCOUNT.getName())
                .setVirtualAccount(virtualAccountDTO);

        return paymentMethod;
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();

        channelProperties.setCustomerName(config.getAccountName())
                .setExpiresAt(expiresAt());

        return channelProperties;
    }

    private String expiresAt() {
        Long accountExpireMinutes = config.getExpireMinutes();
        if (accountExpireMinutes == null) {
            accountExpireMinutes = 40L;
        }
        return LocalDateTimeUtils.beijing2GmtString(LocalDateTime.now().plusMinutes(accountExpireMinutes));
    }
}
