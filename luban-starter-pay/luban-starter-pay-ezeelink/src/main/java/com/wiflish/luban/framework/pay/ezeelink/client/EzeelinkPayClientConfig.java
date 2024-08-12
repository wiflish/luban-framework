package com.wiflish.luban.framework.pay.ezeelink.client;

import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import jakarta.validation.Validator;
import lombok.Data;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * Ezeelink 的 PayClientConfig
 *
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkPayClientConfig implements PayClientConfig {
    private String apiKey;
    private String apiSecret;
    private String partnerId;
    private String subPartnerId;
    private String callbackToken;
    private String baseUrl;
    private String apiUrl;
    private String redirectUrl;
    private String channelCode;
    private Integer expireMinutes;
    private String storeExtId;
    private String terminalId;

    @Override
    public void validate(Validator validator) {
        assertNotNull(baseUrl, "baseUrl 不能为空");
        assertNotNull(apiUrl, "apiUrl 不能为空");
        assertNotNull(apiKey, "apiKey 不能为空");
        assertNotNull(apiSecret, "apiSecret 不能为空");
        assertNotNull(partnerId, "partnerId 不能为空");
        assertNotNull(subPartnerId, "subPartnerId 不能为空");
        assertNotNull(redirectUrl, "redirectUrl 不能为空");
    }
}
