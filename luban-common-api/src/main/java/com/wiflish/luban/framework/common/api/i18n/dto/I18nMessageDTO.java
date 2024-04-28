package com.wiflish.luban.framework.common.api.i18n.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wiflish
 * @since 2024-04-19
 */
@Data
public class I18nMessageDTO {
    private String locale;
    private String code;
    private String message;
    private LocalDateTime updateTime;
}
