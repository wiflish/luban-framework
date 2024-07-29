package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class QrCodeDTO {
    @JSONField(name = "amount")
    private Number amount;

    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "channel_code")
    private String channelCode;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelProperties;
}