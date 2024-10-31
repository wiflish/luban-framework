package com.wiflish.luban.framework.i18n.config;

import com.wiflish.luban.framework.common.api.i18n.I18nApi;
import com.wiflish.luban.framework.i18n.loader.I18nLoader;
import com.wiflish.luban.framework.i18n.loader.I18nLoaderLocalCacheImpl;
import com.wiflish.luban.framework.i18n.spring.DatabaseMessageSource;
import com.wiflish.luban.framework.i18n.spring.I18nMessageSourceFacade;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@ConditionalOnProperty(prefix = "luban.framework.i18n", value = "enable", matchIfMissing = true)
@EnableConfigurationProperties(I18nProperties.class)
@EnableScheduling
public class I18nAutoConfiguration {
    @Bean
    public I18nLoader i18nLoader(I18nApi i18nApi, I18nProperties i18nProperties) {
        return new I18nLoaderLocalCacheImpl(i18nApi, i18nProperties);
    }

    @Bean
    public DatabaseMessageSource messageSource(I18nLoader i18nLoader) {
        return new DatabaseMessageSource(i18nLoader);
    }

    @Bean
    public I18nMessageSourceFacade messageSourceFacade(MessageSource messageSource, I18nProperties i18nProperties, I18nLoader i18nLoader) {
        return new I18nMessageSourceFacade(messageSource, i18nProperties, i18nLoader);
    }
}
