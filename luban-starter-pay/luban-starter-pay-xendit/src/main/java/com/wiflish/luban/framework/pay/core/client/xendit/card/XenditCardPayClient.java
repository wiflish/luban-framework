package com.wiflish.luban.framework.pay.core.client.xendit.card;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPaymentAbstractPayClient;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.xendit.dto.payment.CardDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import com.wiflish.luban.framework.pay.xendit.enums.PaymentTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.wiflish.luban.framework.pay.xendit.enums.XenditConstant.*;

/**
 * @author wiflish
 * @since 2024-07-30
 */
@Slf4j
public class XenditCardPayClient extends XenditPaymentAbstractPayClient {
    public XenditCardPayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, PayChannelEnum.XENDIT_CARD.getCode(), config);
    }

    public XenditCardPayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected PaymentMethodDTO getPaymentMethod(PayOrderUnifiedReqDTO reqDTO) {
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();

        CardDTO.CardInformation cardInformation = new CardDTO.CardInformation();
        Map<String, String> channelExtras = reqDTO.getChannelExtras();
        cardInformation.setCardNumber(channelExtras.get(CARD_NUMBER_KEY));
        cardInformation.setExpiryMonth(channelExtras.get(EXPIRY_MONTH_KEY));
        cardInformation.setExpiryYear(channelExtras.get(EXPIRY_YEAR_KEY));
        cardInformation.setCardholderName(channelExtras.get(CARDHOLDER_NAME_KEY));

        CardDTO cardDTO = new CardDTO();
        cardDTO.setCurrency(reqDTO.getCurrency())
                .setCardInformation(cardInformation)
                .setChannelProperties(channelProperties(reqDTO));

        paymentMethod.setReferenceId(reqDTO.getOutTradeNo())
                .setReusability("ONE_TIME_USE").setType(PaymentTypeEnum.CARD.getName())
                .setCard(cardDTO);

        return paymentMethod;
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();
        channelProperties.setSuccessReturnUrl(reqDTO.getReturnUrl())
                .setFailureReturnUrl(reqDTO.getReturnUrl());

        return channelProperties;
    }
}
