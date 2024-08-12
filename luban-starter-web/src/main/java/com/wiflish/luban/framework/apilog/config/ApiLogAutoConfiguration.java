package com.wiflish.luban.framework.apilog.config;

import com.wiflish.luban.framework.apilog.core.filter.ApiAccessLogFilter;
import com.wiflish.luban.framework.apilog.core.service.ApiAccessLogFrameworkService;
import com.wiflish.luban.framework.apilog.core.service.ApiAccessLogFrameworkServiceImpl;
import com.wiflish.luban.framework.apilog.core.service.ApiErrorLogFrameworkService;
import com.wiflish.luban.framework.apilog.core.service.ApiErrorLogFrameworkServiceImpl;
import com.wiflish.luban.framework.common.api.logger.ApiAccessLogApi;
import com.wiflish.luban.framework.common.api.logger.ApiErrorLogApi;
import com.wiflish.luban.framework.common.enums.WebFilterOrderEnum;
import com.wiflish.luban.framework.web.config.WebAutoConfiguration;
import com.wiflish.luban.framework.web.config.WebProperties;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = WebAutoConfiguration.class)
@EnableConfigurationProperties(ApiLogProperties.class)
public class ApiLogAutoConfiguration {
    @Bean
    public ApiAccessLogFrameworkService apiAccessLogFrameworkService(ApiAccessLogApi apiAccessLogApi, ApiLogProperties apiLogProperties) {
        return new ApiAccessLogFrameworkServiceImpl(apiAccessLogApi, apiLogProperties.getExcludeUrls());
    }

    @Bean
    public ApiErrorLogFrameworkService apiErrorLogFrameworkService(ApiErrorLogApi apiErrorLogApi) {
        return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
    }

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "luban.framework.access-log", value = "enable", matchIfMissing = true)
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogFrameworkService);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

}
