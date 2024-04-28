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
     * 默认语言
     */
    private String language = "en";

    /**
     * 默认货币
     */
    private String currency = "USD";
}
