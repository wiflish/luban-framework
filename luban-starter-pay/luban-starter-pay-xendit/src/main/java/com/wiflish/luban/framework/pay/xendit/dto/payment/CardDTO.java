package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class CardDTO {
    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelPropertiesDTO;

    @JSONField(name = "card_information")
    private CardInformation cardInformation;

    @JSONField(name = "card_verification_results")
    private CardVerificationResults cardVerificationResults;

    @JSONField(name = "card_data_id")
    private String cardDataId;

    @JSONField(name = "is_cvn_submitted")
    private Boolean isCvnSubmitted;


    @Data
    public static class CardInformation {
        @JSONField(name = "token_id")
        private String tokenId;

        @JSONField(name = "masked_card_number")
        private String maskedCardNumber;

        @JSONField(name = "cardholder_name")
        private String cardholderName;

        @JSONField(name = "expiry_month")
        private String expiryMonth;

        @JSONField(name = "expiry_year")
        private String expiryYear;

        @JSONField(name = "type")
        private String type;

        @JSONField(name = "network")
        private String network;

        @JSONField(name = "country")
        private String country;

        @JSONField(name = "issuer")
        private String issuer;

        @JSONField(name = "fingerprint")
        private String fingerprint;
    }

    @Data
    public static class CardVerificationResults {
        @JSONField(name = "address_verification_result")
        private String addressVerificationResult;

        @JSONField(name = "cvv_result")
        private String cvvResult;

        @JSONField(name = "three_d_secure")
        private ThreeDSecure threeDSecure;
    }

    @Data
    public static class ThreeDSecure {
        @JSONField(name = "eci_code")
        private String eciCode;

        @JSONField(name = "three_d_secure_flow")
        private String threeDSecureFlow;

        @JSONField(name = "three_d_secure_result")
        private String threeDSecureResult;

        @JSONField(name = "three_d_secure_result_reason")
        private String threeDSecureResultReason;

        @JSONField(name = "three_d_secure_version")
        private String threeDSecureVersion;
    }
}