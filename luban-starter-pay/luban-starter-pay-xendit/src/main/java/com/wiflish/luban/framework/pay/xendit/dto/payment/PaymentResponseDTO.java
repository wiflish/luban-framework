package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class PaymentResponseDTO {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "amount")
    private Number amount;

    @JSONField(name = "customer_id")
    private String customerId;

    @JSONField(name = "business_id")
    private String businessId;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "payment_method")
    private PaymentMethodDTO paymentMethod;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelProperties;

    @JSONField(name = "actions")
    private List<PaymentActionDTO> actions;

    @JSONField(name = "created")
    private String created;

    @JSONField(name = "updated")
    private String updated;

    @JSONField(name = "metadata")
    private Map<String, Object> metadata;
}