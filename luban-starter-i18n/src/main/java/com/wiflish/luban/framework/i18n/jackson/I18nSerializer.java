package com.wiflish.luban.framework.i18n.jackson;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wiflish.luban.framework.i18n.annotation.I18n;
import com.wiflish.luban.framework.i18n.spring.I18nMessageSourceFacade;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.annotation.AnnotationUtil;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

/**
 * 国际化处理.
 * <p>
 */
@Slf4j
public class I18nSerializer extends StdSerializer<Object> {
    @Resource
    private I18nMessageSourceFacade i18nMessageSourceFacade;

    protected I18nSerializer(Class<Object> t) {
        super(t);
    }

    protected I18nSerializer() {
        this(null);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value instanceof String) {
            // 如果 value 本身是 String 类型，直接进行国际化处理
            gen.writeString(i18nMessageSourceFacade.getMessage((String) value));
        } else { // 处理类注解
            I18n annotation = AnnotationUtil.getAnnotation(value.getClass(), I18n.class);
            if (annotation != null) {
                // 获取注解属性
                boolean cacheable = annotation.cacheable();
                String code = annotation.code();
                Map<String, Object> dynamicMap = i18nMessageSourceFacade.getDynamicMessage(code, ReflectUtil.getFieldValue(value, "id"), cacheable);
                BeanUtil.copyProperties(dynamicMap, value);
            }
            gen.writeStartObject();
            Arrays.stream(ReflectUtil.getFields(value.getClass())).forEach(field -> {
                try {
                    Object fieldValue = ReflectUtil.invoke(value, StrUtil.genGetter(field.getName()));
                    if (field.getAnnotation(I18n.class) != null) {
                        gen.writeObjectField(field.getName(), i18nMessageSourceFacade.getMessage(fieldValue.toString()));
                    } else if (field.getAnnotation(JsonFormat.class) != null){
                        JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
                        String pattern = jsonFormat.pattern();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                        if (fieldValue instanceof java.time.LocalDate) {
                            gen.writeStringField(field.getName(), ((java.time.LocalDate) fieldValue).format(formatter));
                        } else if (fieldValue instanceof java.time.LocalDateTime) {
                            gen.writeStringField(field.getName(), ((java.time.LocalDateTime) fieldValue).format(formatter));
                        } else if (fieldValue instanceof java.time.LocalTime) {
                            gen.writeStringField(field.getName(), ((java.time.LocalTime) fieldValue).format(formatter));
                        } else {
                            gen.writeObjectField(field.getName(), fieldValue);
                        }
                    } else {
                        gen.writeObjectField(field.getName(), fieldValue);
                    }
                } catch (IOException e) {
                    log.warn("serialize i18n error. serialize {}", e.getMessage());
                }
            });
            gen.writeEndObject();
        }
    }



}
