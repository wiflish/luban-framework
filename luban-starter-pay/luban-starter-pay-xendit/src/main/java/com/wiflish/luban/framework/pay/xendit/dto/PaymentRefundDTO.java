package com.wiflish.luban.framework.pay.xendit.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * Payment API中的退款事件请求参数
 */
@Data
public class PaymentRefundDTO {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "payment_id")
    private String paymentId;

    @JSONField(name = "invoice_id")
    private String invoiceId;

    @JSONField(name = "amount")
    private Number amount;

    @JSONField(name = "payment_method_type")
    private String paymentMethodType;

    @JSONField(name = "channel_code")
    private String channelCode;

    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "reason")
    private String reason;

    @JSONField(name = "reference_id")
    private String referenceId;

    @JSONField(name = "failure_code")
    private String failureCode;

    @JSONField(name = "refund_fee_amount")
    private Number refundFeeAmount;

    @JSONField(name = "created")
    private String created;

    @JSONField(name = "updated")
    private String updated;

    @JSONField(name = "metadata")
    private String metadata;
}