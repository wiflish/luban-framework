package com.wiflish.luban.framework.common.validation;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.wiflish.luban.framework.common.util.validation.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
    private String statCode;

    @Override
    public void initialize(Mobile annotation) {
        statCode = annotation.statCode();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果手机号为空，默认不校验，即校验通过
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        String property = SpringUtil.getProperty(statCode);
        if (StrUtil.isEmpty(property)) {
            property = statCode;
        }
        // 校验手机
        return ValidationUtils.isMobile(value, property);
    }

}
