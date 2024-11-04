package com.wiflish.luban.framework.i18n.spring;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.framework.i18n.config.I18nProperties;
import com.wiflish.luban.framework.i18n.loader.I18nLoader;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 代理MessageSource.
 *
 * @author wiflish
 */
@Slf4j
@RequiredArgsConstructor
public class I18nMessageSourceFacade {
    private final MessageSource messageSource;
    private final I18nProperties i18nProperties;
    private final I18nLoader i18nLoader;

    public String getMessage(String code) {
        return getMessage(code, null);
    }

    public String getMessage(String code, Object[] args) {
        return getMessage(null, code, args, "");
    }

    public String getMessage(Locale locale, String code, Object[] args) {
        return getMessage(locale, code, args, "");
    }

    public String getMessage(Locale locale, String code, Object[] args, String defaultMessage) {
        if (locale == null) {
            locale = getLocale();
        }
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    public Map<String, Object> getDynamicMessage(String dynamicCodePrefix, Object id, boolean cacheable) {
        Map<String, Object> dynamicMessageMap = new HashMap<>();
        // 获取动态多语言文本
        String dynamicJsonText = null;
        try {
            // 如果 i18n注解的cacheable是false，则直接查询数据库
            if (cacheable) {
                dynamicJsonText = getMessage(dynamicCodePrefix + "-" + id);
            } else {
                dynamicJsonText = getMessageFromDatabase(dynamicCodePrefix + "-" + id);
            }
        } catch (Exception e) {
            log.warn("get dynamicJsonText error. getDynamicMessage {}", e.getMessage());
            return dynamicMessageMap;
        }
        if (StrUtil.isEmpty(dynamicJsonText) || !JSONUtil.isTypeJSON(dynamicJsonText)) {
            log.debug("dynamicJsonText is null or incorrect format, dynamicJsonText: {}", dynamicJsonText);
            return dynamicMessageMap;
        }
        dynamicMessageMap = JSONUtil.parseObj(dynamicJsonText);
        return dynamicMessageMap;
    }

    public String getMessageFromDatabase(String i18nCode) {
        return i18nLoader.getMessageFromDatabase(getLocale(), i18nCode);
    }

    private Locale getDefaultLocale() {
        String language = i18nProperties.getLanguage();
        if (language.contains("-")) {
            language = language.replaceAll("-", "_");
        }
        return Locale.of(language);
    }

    /**
     * 顺序：请求参数/请求头/默认
     * @return Locale
     */
    private Locale getLocale(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return getDefaultLocale();
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
        return request.getLocale() == null ? getDefaultLocale() : request.getLocale();
    }
}