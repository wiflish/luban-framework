package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-21
 */
@Data
public class EzeelinkBankDTO {
    @JSONField(name = "account_number")
    private String accountNumber;
    @JSONField(name = "bank_code")
    private String bankCode;
    @JSONField(name = "transfer_amount")
    private String transferAmount;
    @JSONField(name = "account_name")
    private String accountName;
    @JSONField(name = "partner_trans_id")
    private String partnerTransId;
    @JSONField(name = "transaction_code")
    private String transactionCode;
    private String status;
    private String message;

}
