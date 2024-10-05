package com.wiflish.luban.framework.pay.core.client.impl;

import cn.hutool.core.lang.Assert;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import jakarta.validation.Validator;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-10-04
 */
@Data
public class PayoutClientConfig implements PayClientConfig {
    private String payoutChannelCode;

    @Override
    public void validate(Validator validator) {
        Assert.notBlank(payoutChannelCode);
    }
}
