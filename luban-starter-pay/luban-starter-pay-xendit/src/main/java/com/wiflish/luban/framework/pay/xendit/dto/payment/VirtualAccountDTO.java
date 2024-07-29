package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class VirtualAccountDTO {
    @JSONField(name = "amount")
    private Number amount;

    @JSONField(name = "channel_code")
    private String channelCode;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelProperties;

    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "alternative_displays")
    private List<AlternativeDisplay> alternativeDisplays;

    @Data
    public static class AlternativeDisplay {
        @JSONField(name = "type")
        private String type;

        @JSONField(name = "data")
        private String data;
    }
}