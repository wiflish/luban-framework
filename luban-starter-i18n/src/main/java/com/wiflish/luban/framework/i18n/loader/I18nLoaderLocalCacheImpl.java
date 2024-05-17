package com.wiflish.luban.framework.i18n.loader;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.framework.common.api.i18n.I18nApi;
import com.wiflish.luban.framework.common.api.i18n.dto.I18nMessageDTO;
import com.wiflish.luban.framework.common.util.date.DateUtils;
import com.wiflish.luban.framework.i18n.config.I18nProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wiflish
 * @since 2024-04-19
 */
@RequiredArgsConstructor
public class I18nLoaderLocalCacheImpl implements I18nLoader {
    /**
     * 刷新频率，单位：毫秒
     */
    private static final int REFRESH_PERIOD = 120 * 1000;

    /**
     * 本地缓存，key为code_语言，value为消息。当key为"code_"，则表示使用默认语言。
     */
    private static final Map<String, String> LOCALE_MESSAGE_CACHE = new ConcurrentHashMap<>();

    private static final Set<Locale> LANGUAGE_CACHE = new HashSet<>();
    private static final Logger log = LoggerFactory.getLogger(I18nLoaderLocalCacheImpl.class);

    /**
     * 最近更新时间，用于增量处理。
     */
    private LocalDateTime latestUpdateTime;

    private final I18nApi i18nApi;
    private final I18nProperties i18nProperties;

    @Override
    public String getMessagePattern(String code) {
        // 先简单点，默认使用已设置的语言。
        String language = i18nProperties.getLanguage();
        Locale locale = Locale.of(language);
        return getMessagePattern(code, locale);
    }

    @Override
    public String getMessagePattern(String code, Locale locale) {
        if (CollUtil.isEmpty(LOCALE_MESSAGE_CACHE)) {
            load(true);
            return code;
        }
        String language = ObjectUtils.isEmpty(locale) ? LocaleContextHolder.getLocale().getLanguage() : locale.getLanguage();

        // 获取缓存中对应语言的消息.
        String messagePattern = LOCALE_MESSAGE_CACHE.get(buildCacheKey(code, language));

        if (StrUtil.isEmpty(messagePattern)) {
            messagePattern = LOCALE_MESSAGE_CACHE.get(buildCacheKey(code, null));
        }

        return ObjectUtils.isEmpty(messagePattern) ? "" : messagePattern;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Async
    @Override
    public void loadAll() {
        load(false);
    }

    @Scheduled(fixedDelay = REFRESH_PERIOD, initialDelay = REFRESH_PERIOD)
    @Override
    public void refreshAll() {
        load(true);
    }

    private void load(boolean isRefresh) {
        // 从数据库中查询所有的国际化资源，暂时不分页。
        List<I18nMessageDTO> allLocaleMessage = i18nApi.getList(latestUpdateTime);
        if (ObjectUtils.isEmpty(allLocaleMessage)) {
            return;
        }
        allLocaleMessage.forEach(i18nMessageDTO -> {
            String locale = i18nMessageDTO.getLocale().toLowerCase();
            String code = i18nMessageDTO.getCode();
            String messagePattern = i18nMessageDTO.getMessage();
            if (StrUtil.isNotEmpty(locale)) {
                LANGUAGE_CACHE.add(Locale.of(locale));
            }
            LOCALE_MESSAGE_CACHE.put(buildCacheKey(code, locale), messagePattern);
            latestUpdateTime = DateUtils.max(latestUpdateTime, i18nMessageDTO.getUpdateTime());
        });
        long localeCount = allLocaleMessage.stream().map(I18nMessageDTO::getLocale).distinct().count();
        log.info("从数据库加载了{}个code，{}个国家。", allLocaleMessage.size(), localeCount);

        if (isRefresh) {
            return;
        }
        int codeCount = 0, fileLocaleCount = 0;
        // 刷新时，只需刷新数据库中的配置，配置文件内容不需要刷新。
        for (Locale locale : LANGUAGE_CACHE) {
            // 按照国家地区来读取本地的国际化资源文件,我们的国际化资源文件放在i18n文件夹之下
            ResourceBundle resourceBundle = null;
            try {
                resourceBundle = ResourceBundle.getBundle("i18n/messages", locale);
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
            if (resourceBundle == null || resourceBundle.keySet().isEmpty()) {
                continue;
            }
            fileLocaleCount++;
            // 获取国际化资源文件中的key和value
            Set<String> keySet = resourceBundle.keySet();
            for (String key : keySet) {
                LOCALE_MESSAGE_CACHE.put(buildCacheKey(key, locale.getLanguage()), resourceBundle.getString(key));
                codeCount++;
            }
        }
        log.info("从配置中加载了{}个code，{}个国家。", codeCount, fileLocaleCount);
    }

    private String buildCacheKey(String code, String locale) {
        if (StrUtil.isEmpty(locale)) {
            return (code + "_").toLowerCase();
        }
        return (code + "_" + locale).toLowerCase();
    }
}
