package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class EWalletDTO {
    @JSONField(name = "account")
    private Account account;

    @JSONField(name = "channel_code")
    private String channelCode;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelProperties;

    @Data
    public static class Account {
        @JSONField(name = "name")
        private String name;

        @JSONField(name = "balance")
        private Number balance;

        @JSONField(name = "point_balance")
        private Number pointBalance;

        @JSONField(name = "account_details")
        private String accountDetails;
    }
}