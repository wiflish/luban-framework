package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkBaseDTO {
    @JSONField(name = "partner_id")
    private String partnerId;
    @JSONField(name = "sub_partner_id")
    private String subPartnerId;
    @JSONField(name = "transaction_id")
    private String transactionId;
    @JSONField(name = "transaction_code")
    private String transactionCode;
    @JSONField(name = "amount")
    private String amount;
    @JSONField(name = "status")
    private String status;
    @JSONField(name = "expired_time")
    private String expiredTime;

    /**
     * Callback 使用的字段.
     */
    private String timestamp;
    private String signature;
    private String requestBody;
    private String callbackUrl;
}
