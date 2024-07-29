package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developers.xendit.co/api-reference/payments-api/#payment-request-object">PaymentRequest</a>
 *
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class PaymentRequestDTO {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "business_id")
    private String businessId;

    @JSONField(name = "customer_id")
    private String customerId;

    @JSONField(name = "reference_id")
    private String referenceId;

    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "amount")
    private Number amount;

    @JSONField(name = "country")
    private String country;

    /**
     * REQUIRES_ACTION - The request passed validation but requires additional steps in order to complete the payment. Typical actions are for merchant to trigger OTP validation or redirect your customer to an authentication page.
     * PENDING - The transaction passed initial validation and the payment channel is currently processing the transaction.
     * SUCCEEDED - The payment was successfully completed.
     * FAILED - The payment request failed. See failure_code for the specific reason why the transaction failed.
     * AWAITING_CAPTURE - The payment request is eligible for manual capture and is awaiting the trigger of the manual Capture API.
     */
    @JSONField(name = "status")
    private String status;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "payment_method")
    private PaymentMethodDTO paymentMethod;

    @JSONField(name = "actions")
    private List<PaymentActionDTO> actions = List.of();

    /**
     * AUTOMATIC
     * MANUAL - Only supported for payment method type CARD
     */
    @JSONField(name = "capture_method")
    private String captureMethod;

    /**
     * CUSTOMER - default, The transaction was initiated by the payor
     * MERCHANT - The transaction was initiated by the merchant
     */
    @JSONField(name = "initiator")
    private String initiator;

    @JSONField(name = "card_verification_results")
    private CardDTO.CardVerificationResults cardVerificationResults;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelPropertiesDTO;

    @JSONField(name = "shipping_information")
    private Map<String, Object> shippingInformation;

    @JSONField(name = "failure_code")
    private String failureCode;

    @JSONField(name = "created")
    private String created;

    @JSONField(name = "updated")
    private String updated;

    @JSONField(name = "metadata")
    private Map<String, Object> metadata;

}