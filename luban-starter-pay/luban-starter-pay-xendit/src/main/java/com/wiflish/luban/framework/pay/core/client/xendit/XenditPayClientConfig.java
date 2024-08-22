package com.wiflish.luban.framework.pay.core.client.xendit;

import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import jakarta.validation.Validator;
import lombok.Data;

/**
 * Xendit 的 PayClientConfig
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Data
public class XenditPayClientConfig implements PayClientConfig {
    private String apiKey;
    private String callbackToken;
    private String channelCode;
    private String baseUrl;
    private String successUrl;
    private String accountName;
    private Long expireMinutes;

    @Override
    public void validate(Validator validator) {
    }
}
