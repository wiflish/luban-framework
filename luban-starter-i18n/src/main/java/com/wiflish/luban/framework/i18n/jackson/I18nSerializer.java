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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Arrays;
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
        } else { // 处理类注解
            Map<String, Object> i18nMap = getI18nMap(value);
            BeanUtil.copyProperties(i18nMap, value);
            gen.writeStartObject();
            Arrays.stream(ReflectUtil.getFields(value.getClass())).forEach(field -> {
                try {
                    if (field.getAnnotation(I18n.class) != null){
                        gen.writeObjectField(field.getName(), getI18nStringValue(ReflectUtil.invoke(value, StrUtil.genGetter(field.getName()))));
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

    private Map<String, Object> getI18nMap(Object value) {
        Object id = ReflectUtil.getFieldValue(value, "id");
        Map<String, Object> i18nMap = new HashMap<>();
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
            log.warn("get i18nJsonText error. getI18nMap {}", e.getMessage());
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

    private String getI18nStringValue(String stringValue) {
        if (stringValue != null) {
            try {
                return i18nMessageSourceFacade.getMessage(getLocale(), stringValue, null);
            } catch (Exception e) {
                log.warn("getI18nStringValue error. {}", e.getMessage());
            }
        }
        return stringValue;
    }

    /**
     * 顺序：请求参数/请求头/默认
     * @return Locale
     */
    private Locale getLocale(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return i18nMessageSourceFacade.getDefaultLocale();
        }
        HttpServletRequest request = requestAttributes.getRequest();
        // 1. 从请求参数中获取区域设置（在管理端多语言编辑页面需要通过参数指定显示）
        String paramLocale = request.getParameter("locale");
        if (StrUtil.isNotEmpty(paramLocale)) {
            return Locale.of(paramLocale);
        }

        // 1. 从cookie中获取区域设置
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!"locale".equals(cookie.getName())) {
                    continue;
                }
                String cookieLocale = cookie.getValue();
                if (StrUtil.isNotEmpty(cookieLocale)) {
                    return Locale.of(cookieLocale);
                }
            }
        }
        return request.getLocale() == null ? i18nMessageSourceFacade.getDefaultLocale() : request.getLocale();
    }
}
