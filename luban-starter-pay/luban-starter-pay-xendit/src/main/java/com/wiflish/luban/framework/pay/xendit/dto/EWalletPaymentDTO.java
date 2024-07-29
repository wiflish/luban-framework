package com.wiflish.luban.framework.pay.xendit.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import lombok.Data;

@Data
public class EWalletPaymentDTO {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "basket")
    private String basket;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "actions")
    private Actions actions;

    @JSONField(name = "created")
    private String created;

    @JSONField(name = "updated")
    private String updated;

    @JSONField(name = "currency")
    private String currency;

    @JSONField(name = "metadata")
    private Metadata metadata;

    @JSONField(name = "voided_at")
    private String voidedAt;

    @JSONField(name = "capture_now")
    private Boolean captureNow;

    @JSONField(name = "customer_id")
    private String customerId;

    @JSONField(name = "callback_url")
    private String callbackUrl;

    @JSONField(name = "channel_code")
    private String channelCode;

    @JSONField(name = "failure_code")
    private String failureCode;

    @JSONField(name = "reference_id")
    private String referenceId;

    @JSONField(name = "charge_amount")
    private Integer chargeAmount;

    @JSONField(name = "capture_amount")
    private Integer captureAmount;

    @JSONField(name = "checkout_method")
    private String checkoutMethod;

    @JSONField(name = "payment_method_id")
    private String paymentMethodId;

    @JSONField(name = "channel_properties")
    private ChannelPropertiesDTO channelProperties;

    @JSONField(name = "is_redirect_required")
    private Boolean isRedirectRequired;

    @Data
    public static class Actions {
        @JSONField(name = "mobile_web_checkout_url")
        private String mobileWebCheckoutUrl;

        @JSONField(name = "desktop_web_checkout_url")
        private String desktopWebCheckoutUrl;

        @JSONField(name = "mobile_deeplink_checkout_url")
        private String mobileDeeplinkCheckoutUrl;
    }

    @Data
    public static class Metadata {
        @JSONField(name = "branch_code")
        private String branchCode;
    }
}