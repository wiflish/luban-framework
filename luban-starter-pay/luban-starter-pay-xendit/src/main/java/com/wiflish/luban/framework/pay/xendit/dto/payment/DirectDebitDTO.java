package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class DirectDebitDTO {
    @JSONField(name = "type")
    private String type;

    @JSONField(name = "debit_card")
    private DebitCardDTO debitCard;

    @JSONField(name = "bank_account")
    private BankAccount bankAccount;

    @JSONField(name = "channel_code")
    private String channelCode;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelProperties;

    @Data
    public static class BankAccount {
        @JSONField(name = "bank_account_hash")
        private String bankAccountHash;

        @JSONField(name = "masked_bank_account_number")
        private String maskedBankAccountNumber;
    }
}