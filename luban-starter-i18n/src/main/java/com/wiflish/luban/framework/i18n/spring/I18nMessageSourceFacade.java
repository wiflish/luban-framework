package com.wiflish.luban.framework.i18n.spring;

import com.wiflish.luban.framework.i18n.config.I18nProperties;
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

    public String getMessage(String code) {
        return getMessage(code, null);
    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = Locale.of(i18nProperties.getLanguage());
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}