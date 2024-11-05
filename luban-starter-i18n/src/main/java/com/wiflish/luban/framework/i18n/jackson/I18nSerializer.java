package com.wiflish.luban.framework.i18n.jackson;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wiflish.luban.framework.i18n.annotation.I18n;
import com.wiflish.luban.framework.i18n.spring.I18nMessageSourceFacade;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.annotation.AnnotationUtil;

import java.io.IOException;
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
                    if (field.getAnnotation(I18n.class) != null){
                        gen.writeObjectField(field.getName(), i18nMessageSourceFacade.getMessage(ReflectUtil.invoke(value, StrUtil.genGetter(field.getName()))));
                    } else {
                        gen.writeObjectField(field.getName(), ReflectUtil.invoke(value, StrUtil.genGetter(field.getName())));
                    }
                } catch (IOException e) {
                    log.warn("serialize i18n error. serialize {}", e.getMessage());
                }
            });
            gen.writeEndObject();
        }
    }



}
