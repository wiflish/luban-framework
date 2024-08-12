package com.wiflish.luban.framework.apilog.core.service;

import cn.hutool.core.bean.BeanUtil;
import com.wiflish.luban.framework.common.api.logger.ApiAccessLogApi;
import com.wiflish.luban.framework.common.api.logger.dto.ApiAccessLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * API 访问日志 Framework Service 实现类
 *
 * 基于 {@link ApiAccessLogApi} 服务，记录访问日志
 *
 * @author wiflish
 */
@RequiredArgsConstructor
public class ApiAccessLogFrameworkServiceImpl implements ApiAccessLogFrameworkService {
    private final ApiAccessLogApi apiAccessLogApi;
    private final List<String> excludeUrls;

    @Override
    @Async
    public void createApiAccessLog(ApiAccessLog apiAccessLog) {
        ApiAccessLogCreateReqDTO reqDTO = BeanUtil.copyProperties(apiAccessLog, ApiAccessLogCreateReqDTO.class);
        apiAccessLogApi.createApiAccessLog(reqDTO);
    }

    @Override
    public String[] getExcludeUrls() {
        if (excludeUrls == null) {
            return new String[0];
        }
        return excludeUrls.toArray(new String[0]);
    }
}
