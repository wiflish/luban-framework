package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Map;

/**
 * Payment API中的退款事件请求参数
 */
@Data
public class PaymentRefundReqDTO {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "payment_request_id")
    private String paymentRequestId;

    @JSONField(name = "reference_id")
    private String referenceId;

    @JSONField(name = "invoice_id")
    private String invoiceId;

    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "amount")
    private Number amount;

    /**
     * FRAUDULENT
     * DUPLICATE
     * REQUESTED_BY_CUSTOMER
     * CANCELLATION
     * OTHERS
     */
    @JSONField(name = "reason")
    private String reason = "REQUESTED_BY_CUSTOMER";

    @JSONField(name = "metadata")
    private Map<String, Object> metadata;
}