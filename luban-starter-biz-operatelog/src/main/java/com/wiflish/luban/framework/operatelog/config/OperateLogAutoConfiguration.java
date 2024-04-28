package com.wiflish.luban.framework.operatelog.config;

import com.wiflish.luban.framework.operatelog.core.aop.OperateLogAspect;
import com.wiflish.luban.framework.operatelog.core.service.OperateLogFrameworkService;
import com.wiflish.luban.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import com.wiflish.luban.framework.common.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class OperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
