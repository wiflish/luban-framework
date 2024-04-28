package com.wiflish.luban.framework.common.api.errorcode.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 错误码自动生成 DTO
 *
 * @author dylan
 */
@Data
@Accessors(chain = true)
public class ErrorCodeAutoGenerateReqDTO {

    /**
     * 应用名
     */
    @NotNull(message = "应用名不能为空")
    private String applicationName;
    /**
     * 错误码编码
     */
    @NotNull(message = "错误码编码不能为空")
    private Integer code;
    /**
     * 错误码错误提示
     */
    @NotEmpty(message = "错误码错误提示不能为空")
    private String message;

}
