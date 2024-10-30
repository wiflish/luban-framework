package com.wiflish.luban.framework.i18n.jackson;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wiflish.luban.framework.i18n.annotation.I18n;
import com.wiflish.luban.framework.i18n.spring.I18nMessageSourceFacade;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
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
            // 如果 value 本身是 String 类型且带有 @I18n 注解，直接进行国际化处理
            handleStringField(gen, (String) value);
        } else if (value.getClass().isAnnotationPresent(I18n.class)) {
            // 处理带有 @I18n 注解的复杂对象
            // value属性转换到Map
            Map<String, Object> objectMap = convertObjectToMap(value);
            Map<String, Object> i18nMap = getI18nMap(value, objectMap);
            handleObjectStringField(gen, objectMap, i18nMap);
        } else {
            // 如果对象或属性没有 @I18n 注解，使用默认的序列化器
            log.warn("value is not String or has no @I18n annotation, value: {}", value);
            gen.writeObject(value);
        }
    }

    private Map<String, Object> getI18nMap(Object value, Map<String, Object> objectMap) {
        Map<String, Object> i18nMap = new HashMap<>();
        Object id = objectMap.get("id");
        if (id == null) {
            log.debug("id is null, object: {}", objectMap);
            return i18nMap;
        }
        // 组装国际化code
        String i18nCode = getI18nCode(value, id);
        // 获取动态多语言文本
        String i18nJsonText = null;
        try {
            i18nJsonText = i18nMessageSourceFacade.getMessage(getLocale(), i18nCode, null);
        } catch (Exception e) {
            log.warn("object deserialize i18n error. handleStringField {}", e.getMessage());
        }
        if (StrUtil.isEmpty(i18nJsonText) || !JSONUtil.isTypeJSON(i18nJsonText)) {
            log.debug("i18nJsonText is null or incorrect format, i18nJsonText: {}", i18nJsonText);
            return i18nMap;
        }
        i18nMap = JSONUtil.parseObj(i18nJsonText);
        return i18nMap;
    }

    private static String getI18nCode(Object value, Object id) {
        return value.getClass().getAnnotation(I18n.class).code() + "_" + id;
    }

    private static void handleObjectStringField(JsonGenerator gen, Map<String, Object> objectMap, Map<String, Object> i18nMap) throws IOException {
        gen.writeStartObject();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            if (fieldValue instanceof String && i18nMap.containsKey(fieldName)) {
                fieldValue = i18nMap.get(fieldName);
            }
            gen.writeObjectField(fieldName, fieldValue);
        }
        gen.writeEndObject();
    }

    private void handleStringField(JsonGenerator gen, String stringValue) throws IOException {
        String i18nStringValue = stringValue;
        if (stringValue != null) {
            try {
                i18nStringValue = i18nMessageSourceFacade.getMessage(getLocale(), stringValue, null);
            } catch (Exception e) {
                log.warn("deserialize i18n error. handleStringField {}", e.getMessage());
            }
        }
        gen.writeString(i18nStringValue);
    }

    private static Map<String, Object> convertObjectToMap(Object value) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = value.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(value));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field " + field.getName(), e);
            }
        }
        return map;
    }

    /**
     * 顺序：请求参数/请求头/默认
     * @return
     */
    private Locale getLocale(){
        Locale locale = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String paramLocale = request.getParameter("locale");
            if (StrUtil.isNotEmpty(paramLocale)) {
                locale = Locale.of(paramLocale);
            } else {
                locale = request.getLocale();
            }
        }
        if (locale == null) {
            locale = i18nMessageSourceFacade.getDefaultLocale();
        }
        return locale;
    }


}
