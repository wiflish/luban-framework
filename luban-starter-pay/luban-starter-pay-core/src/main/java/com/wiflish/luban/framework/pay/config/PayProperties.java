package com.wiflish.luban.framework.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wiflish
 * @since 2024-08-15
 */
@Data
@ConfigurationProperties(prefix = "luban.framework.pay")
public class PayProperties {
    /**
     * 是否开启
     */
    private Boolean enable = true;

    /**
     * 货币编码
     */
    private String currencyCode = "CNY";
}
