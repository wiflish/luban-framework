package com.wiflish.luban.framework.i18n.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * I18n配置
 *
 * @author wiflish
 */
@ConfigurationProperties(prefix = "luban.framework.i18n")
@Data
public class I18nProperties {
    private static final Boolean ENABLE_DEFAULT = true;

    /**
     * 是否开启
     */
    private Boolean enable = ENABLE_DEFAULT;

    /**
     * 语言
     */
    private String language = "zh-CN";

    /**
     * 货币
     */
    private String currency = "¥";

    /**
     * 货币编码
     */
    private String currencyCode = "CNY";

    /**
     * 国家编码
     */
    private String stateCode = "CN";
}
