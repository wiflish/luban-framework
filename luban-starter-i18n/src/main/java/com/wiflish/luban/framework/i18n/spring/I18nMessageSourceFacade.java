package com.wiflish.luban.framework.i18n.spring;

import com.wiflish.luban.framework.i18n.config.I18nProperties;
import com.wiflish.luban.framework.i18n.loader.I18nLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * 代理MessageSource.
 *
 * @author wiflish
 */
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
            locale = getDefaultLocale();
        }
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    public Locale getDefaultLocale() {
        String language = i18nProperties.getLanguage();
        if (language.contains("-")) {
            language = language.replaceAll("-", "_");
        }
        return Locale.of(language);
    }

    public String getMessageFromDatabase(Locale locale, String i18nCode) {
        return i18nLoader.getMessageFromDatabase(locale, i18nCode);
    }
}