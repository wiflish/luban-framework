package com.wiflish.luban.framework.i18n.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wiflish.luban.framework.i18n.jackson.I18nSerializer;

import java.lang.annotation.*;

/**
 * i18n注解，在类属性上使用，把字段的值进行国际化处理
 *
 * @author wiflish
 * @since 2024-07-04
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside // 此注解是其他所有 jackson 注解的元注解，打上了此注解的注解表明是 jackson 注解的一部分
@JsonSerialize(using = I18nSerializer.class)
public @interface I18n {
    String code() default "";
    boolean cacheable() default true;
}
