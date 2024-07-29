package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * <a href="https://developers.xendit.co/api-reference/payments-api/#payment-method-object">payment-method-object</a>
 *
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class OverTheCounterDTO {
    @JSONField(name = "amount")
    private Number amount;

    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "channel_code")
    private String channelCode;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelProperties;

}