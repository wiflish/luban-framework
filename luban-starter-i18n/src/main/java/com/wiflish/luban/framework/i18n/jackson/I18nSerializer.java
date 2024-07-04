package com.wiflish.luban.framework.i18n.jackson;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.wiflish.luban.framework.i18n.annotation.I18n;
import com.wiflish.luban.framework.i18n.spring.I18nMessageSourceFacade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 国际化处理.
 * <p>
 */
@Slf4j
public class I18nSerializer extends JsonSerializer<String> {
    public static final I18nSerializer INSTANCE = new I18nSerializer();

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        I18nMessageSourceFacade bean = SpringUtil.getBean(I18nMessageSourceFacade.class);
        if (bean == null) {
            gen.writeString(value);
            return;
        }
        try {
            Field field = getField(gen);
            I18n annotation = field.getAnnotation(I18n.class);
            if (annotation == null) {
                gen.writeString(value);
                return;
            }
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                gen.writeString(bean.getMessage(request.getLocale(), value, null));
            } else {
                gen.writeString(bean.getMessage(value, null));
            }
        } catch (Exception e) {
            log.warn("deserialize i18n error. {}", e.getMessage());
        }
    }

    /**
     * 获取字段
     *
     * @param generator JsonGenerator
     * @return 字段
     */
    private Field getField(JsonGenerator generator) {
        String currentName = generator.getOutputContext().getCurrentName();
        Object currentValue = generator.getCurrentValue();
        Class<?> currentValueClass = currentValue.getClass();
        return ReflectUtil.getField(currentValueClass, currentName);
    }
}
