package com.wiflish.luban.framework.i18n.loader;

import java.util.Locale;

/**
 * @author wiflish
 * @since 2024-04-19
 */
public interface I18nLoader {
    void loadAll();
    void refreshAll();
    String getMessagePattern(String code);
    String getMessagePattern(String code, Locale locale);
}
