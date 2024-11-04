package com.wiflish.luban.framework.i18n.annotation;

import java.lang.annotation.*;

/**
 * i18n注解，在类属性上使用，把字段的值进行国际化处理
 *
 * @author wiflish
 * @since 2024-07-04
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nDynamicPoint {
    String code() default "";
    boolean cacheable() default true;
}
