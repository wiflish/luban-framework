package com.wiflish.luban.framework.i18n.spring;

import com.wiflish.luban.framework.i18n.loader.I18nLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractMessageSource;

import java.text.MessageFormat;
import java.util.Locale;

@RequiredArgsConstructor
public class DatabaseMessageSource extends AbstractMessageSource {
    private final I18nLoader i18nLoader;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String messagePattern = i18nLoader.getMessagePattern(code, locale);
        return new MessageFormat(messagePattern, locale);
    }
}