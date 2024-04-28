package com.wiflish.luban.framework.errorcode.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 错误码的配置属性类
 *
 * @author dlyan
 */
@ConfigurationProperties("luban.framework.error-code")
@Data
@Validated
public class ErrorCodeProperties {

    /**
     * 是否开启
     */
    private Boolean enable = true;
    /**
     * 错误码枚举类
     */
    @NotNull(message = "错误码枚举类不能为空")
    private List<String> constantsClassList;

}
