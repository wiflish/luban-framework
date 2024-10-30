package com.wiflish.luban.framework.common.api.i18n.dto;

import lombok.Data;

@Data
public class SystemI18nDynamicDTO {

    private String locale;

    private String code;

    private String subCode;

    private String message;

    private String remark;

}