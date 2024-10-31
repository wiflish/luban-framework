package com.wiflish.luban.framework.i18n.jackson;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.wiflish.luban.framework.i18n.annotation.I18n;
import com.wiflish.luban.framework.i18n.spring.I18nMessageSourceFacade;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            // 如果 value 本身是 String 类型，直接进行国际化处理
            gen.writeString(getI18nStringValue((String) value));
        } else {
            // value属性转换到Map
            Map<String, Object> objectMap = new HashMap<>();// 所有属性
            Map<String, Object> annotationsPropertyMap = new HashMap<>();// 带有I18n注解的属性
            convertObjectToMap(value, objectMap, annotationsPropertyMap);
            // 获取多语言包
            Map<String, Object> i18nMap = getI18nMap(value, objectMap);
            // 序列化输出
            handleObjectStringField(gen, objectMap, annotationsPropertyMap, i18nMap);
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
        String i18nCode = getObjectI18nCode(value, id);
        // 获取动态多语言文本
        String i18nJsonText = null;
        try {
            // 如果 i18n注解的cacheable是false，则直接查询数据库
            if (value.getClass().getAnnotation(I18n.class).cacheable()) {
                i18nJsonText = getI18nStringValue(i18nCode);
            } else {
                i18nJsonText = i18nMessageSourceFacade.getMessageFromDatabase(getLocale(), i18nCode);
            }
        } catch (Exception e) {
            log.warn("object deserialize i18n error. handleStringField {}", e.getMessage());
            return i18nMap;
        }
        if (StrUtil.isEmpty(i18nJsonText) || !JSONUtil.isTypeJSON(i18nJsonText)) {
            log.debug("i18nJsonText is null or incorrect format, i18nJsonText: {}", i18nJsonText);
            return i18nMap;
        }
        i18nMap = JSONUtil.parseObj(i18nJsonText);
        return i18nMap;
    }

    private static String getObjectI18nCode(Object value, Object id) {
        return value.getClass().getAnnotation(I18n.class).code() + "-" + id;
    }

    private void handleObjectStringField(JsonGenerator gen, Map<String, Object> objectMap, Map<String, Object> annotationsPropertyMap, Map<String, Object> i18nMap) throws IOException {
        gen.writeStartObject();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            if (annotationsPropertyMap.containsKey(fieldName)) {
                gen.writeObjectField(fieldName, getI18nStringValue((String) fieldValue));
            } else {
                if (i18nMap.containsKey(fieldName)) {
                    fieldValue = i18nMap.get(fieldName);
                }
                gen.writeObjectField(fieldName, fieldValue);
            }
        }
        gen.writeEndObject();
    }

    private String getI18nStringValue(String stringValue) throws IOException {
        String i18nStringValue = stringValue;
        if (stringValue != null) {
            try {
                i18nStringValue = i18nMessageSourceFacade.getMessage(getLocale(), stringValue, null);
            } catch (Exception e) {
                log.warn("deserialize i18n error. handleStringField {}", e.getMessage());
            }
        }
        return i18nStringValue;
    }

    private static void convertObjectToMap(Object value, Map<String, Object> objectMap, Map<String, Object> annotationsPropertyMap) {
        Class<?> clazz = value.getClass();
        // 递归处理类及其所有父类的字段
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                try {
                    Method getterMethod = clazz.getMethod(getterMethodName);
                    Object fieldValue = getterMethod.invoke(value);

                    if (field.isAnnotationPresent(I18n.class)) {
                        annotationsPropertyMap.put(fieldName, fieldValue);
                    }
                    objectMap.put(fieldName, fieldValue);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Error accessing field or method " + fieldName, e);
                }
            }
            clazz = clazz.getSuperclass(); // 获取父类
        }
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

            // 1. 从请求参数中获取区域设置
            String paramLocale = request.getParameter("locale");
            if (StrUtil.isNotEmpty(paramLocale)) {
                locale = Locale.of(paramLocale);
            } else {
                // 2. 从 Cookie 中获取区域设置
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("locale".equals(cookie.getName())) {
                            String cookieLocale = cookie.getValue();
                            if (StrUtil.isNotEmpty(cookieLocale)) {
                                locale = Locale.of(cookieLocale);
                                break;
                            }
                        }
                    }
                }

                // 3. 从请求的 Accept-Language 头中获取区域设置
                if (locale == null) {
                    locale = request.getLocale();
                }
            }
        }

        // 4. 如果仍未获取到区域设置，使用默认区域设置
        if (locale == null) {
            locale = i18nMessageSourceFacade.getDefaultLocale();
        }
        return locale;
    }


}
