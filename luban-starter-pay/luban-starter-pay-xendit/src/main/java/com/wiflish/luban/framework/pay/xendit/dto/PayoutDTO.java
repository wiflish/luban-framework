package com.wiflish.luban.framework.pay.xendit.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * Payout DTO
 *
 * @author wiflish
 * @since 2024-07-19
 */
@Data
public class PayoutDTO {
    private String id;
    private String created;
    private String updated;
    @JSONField(name = "reference_id")
    private String referenceId;
    @JSONField(name = "business_id")
    private String businessId;
    @JSONField(name = "channel_code")
    private String channelCode;
    @JSONField(name = "channel_properties")
    private ChannelProperties channelProperties;
    private String currency;
    private int amount;
    private String description;
    @JSONField(name = "receipt_notification")
    private String receiptNotification;
    private String status;
    @JSONField(name = "estimated_arrival_time")
    private String estimatedArrivalTime;
    @JSONField(name = "failure_code")
    private String failureCode;
    private String metadata;

    @Data
    public static class ChannelProperties {
        @JSONField(name = "account_number")
        private String accountNumber;
        @JSONField(name = "account_holder_name")
        private String accountHolderName;
    }
}