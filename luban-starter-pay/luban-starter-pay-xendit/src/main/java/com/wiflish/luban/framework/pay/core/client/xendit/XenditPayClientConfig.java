package com.wiflish.luban.framework.pay.core.client.xendit;

import cn.hutool.core.lang.Assert;
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
    private String successUrl;

    @Override
    public void validate(Validator validator) {
        Assert.notBlank(apiKey);
        Assert.notBlank(callbackToken);
        Assert.notBlank(channelCode);
    }
}
