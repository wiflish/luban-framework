package com.wiflish.luban.framework.pay.xendit.dto;

import lombok.Data;

/**
 * Invoices paid webhook的请求参数.
 * <p>
 * <a href="https://dashboard.xendit.co/settings/developers#webhooks">Invoices paid</a>
 * </p>
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Data
public class InvoicesPaidEventDTO {
    private String id;
    private String external_id;
    private String user_id;
    private Boolean is_high;
    private String payment_method;
    private String status;
    private String merchant_name;
    private Integer amount;
    private Integer paid_amount;
    private String bank_code;
    /**
     * 格林威治标准时间，需要转化
     */
    private String paid_at;
    private String payer_email;
    private String description;
    private Integer adjusted_received_amount;
    private Integer fees_paid_amount;
    private String updated;
    private String created;
    private String currency;
    private String payment_channel;
    private String payment_destination;
}
