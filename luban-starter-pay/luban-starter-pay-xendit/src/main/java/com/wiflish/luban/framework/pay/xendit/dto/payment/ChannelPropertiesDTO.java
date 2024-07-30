package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class ChannelPropertiesDTO {
    @JSONField(name = "skip_three_d_secure")
    private Boolean skipThreeDSecure;

    @JSONField(name = "success_return_url")
    private String successReturnUrl;

    @JSONField(name = "success_redirect_url")
    private String successRedirectUrl;

    @JSONField(name = "failure_return_url")
    private String failureReturnUrl;

    @JSONField(name = "cardonfile_type")
    private String cardOnFileType;

    @JSONField(name = "cancel_return_url")
    private String cancelReturnUrl;

    @JSONField(name = "pending_return_url")
    private String pendingReturnUrl;

    @JSONField(name = "mobile_number")
    private String mobileNumber;

    @JSONField(name = "customer_name")
    private String customerName;

    @JSONField(name = "payment_code")
    private String paymentCode;

    @JSONField(name = "cashtag")
    private String cashTag;

    @JSONField(name = "expires_at")
    private String expiresAt;

    @JSONField(name = "redeem_points")
    private String redeemPoints;

    @JSONField(name = "qr_string")
    private String qrString;

    /**
     * "true"
     * "false"
     */
    @JSONField(name = "require_auth")
    private String requireAuth;

    @JSONField(name = "virtual_account_number")
    private String virtualAccountNumber;
    @JSONField(name = "account_number")
    private String accountNumber;
    @JSONField(name = "account_holder_name")
    private String accountHolderName;
}
