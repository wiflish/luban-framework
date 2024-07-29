package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developers.xendit.co/api-reference/payments-api/#payment-method-object">payment-method-object</a>
 *
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class PaymentMethodDTO {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "reference_id")
    private String referenceId;

    @JSONField(name = "customer_id")
    private String customerId;

    @JSONField(name = "business_id")
    private String businessId;

    /**
     * - ONE_TIME_USE
     * - MULTIPLE_USE
     */
    @JSONField(name = "reusability")
    private String reusability;

    @JSONField(name = "country")
    private String country;

    /**
     * REQUIRES_ACTION - The request passed validation but requires additional steps in order to activate the payment method for use. Typical actions are for merchant to trigger OTP validation or redirect your customer to authentication page.
     * ACTIVE - The payment method can be used for payment requests (for Cards, E-wallets, Direct Debit) or can now accept payments (for Virtual Account, Over-the-Counter, QR Code)
     * INACTIVE - Merchant-toggled status to temporarily prevent further transactions from the payment method. This status is reversible.
     * EXPIRED - The underlying authorization has expired, invalidated, or has been unlinked. This status is not reversible.
     * PENDING - The request is successfully passed, and need to acivate asynchronously. Please listen to our callback to get the updated status.
     * FAILED - For Cards Payment Methods, this means the card was not successfully tokenized. For Direct Debit Payment Methods, this indicates that the account is already linked for the cend customer.
     */
    @JSONField(name = "status")
    private String status;

    private List<PaymentActionDTO> actions = List.of();

    /**
     * EWALLET
     * DIRECT_DEBIT
     * CARD
     * VIRTUAL_ACCOUNT
     * OVER_THE_COUNTER
     * QR_CODE
     */
    @JSONField(name = "type")
    private String type;

    @JSONField(name = "card")
    private CardDTO card;

    @JSONField(name = "ewallet")
    private EWalletDTO ewallet;

    @JSONField(name = "direct_debit")
    private DirectDebitDTO directDebit;

    @JSONField(name = "direct_bank_transfer")
    private Object directBankTransfer;

    @JSONField(name = "over_the_counter")
    private OverTheCounterDTO overTheCounter;

    @JSONField(name = "virtual_account")
    private VirtualAccountDTO virtualAccount;

    @JSONField(name = "qr_code")
    private QrCodeDTO qrCode;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "failure_code")
    private String failureCode;

    @JSONField(name = "metadata")
    private Map<String, Object> metadata;

    @JSONField(name = "created")
    private String created;

    @JSONField(name = "updated")
    private String updated;
}
