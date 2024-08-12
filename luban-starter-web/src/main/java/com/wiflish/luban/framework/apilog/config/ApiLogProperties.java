package com.wiflish.luban.framework.apilog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author wiflish
 * @since 2024-08-12
 */
@Data
@ConfigurationProperties(prefix = "luban.framework.access-log")
public class ApiLogProperties {
    private Boolean enable = true;
    private List<String> excludeUrls = List.of();
}
