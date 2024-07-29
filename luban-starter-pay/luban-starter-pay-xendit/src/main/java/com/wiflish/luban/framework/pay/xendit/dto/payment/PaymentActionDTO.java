package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * <a href="https://developers.xendit.co/api-reference/payments-api/#payment-method-object">payment-method-object</a>
 *
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class PaymentActionDTO {
    /**
     * POST | GET
     */
    private String method;
    /**
     * API - The provided url is a server-side API, merchant will need to provide necessary information to the API
     * WEB - The provided redirect url is optimized for desktop or web interface. This can also be used if no MOBILE url is provided. Merchant will need to redirect their end user to this page to complete payment authentication.
     * MOBILE - The provided redirect url is optimized for mobile devices. Merchant will need detect the mobile device and redirect their end user to this page to complete payment authentication.
     * DEEPLINK - The provided redirect url utilizes deep linking to the channel partner’s platform. Merchant will need detect the mobile device and redirect their end user to this page to complete payment authentication.
     */
    @JSONField(name = "url_type")
    private String urlType;
    /**
     * AUTH - Trigger this action in order to authorize linking or payment.
     * RESEND_AUTH - Trigger this action in order to resend the authorization code to the end-customer.
     */
    private String action;

    private String url;

    public boolean isMobileUrl() {
        return "MOBILE".equals(this.urlType);
    }

    public boolean isWebUrl() {
        return "WEB".equals(this.urlType);
    }
}
