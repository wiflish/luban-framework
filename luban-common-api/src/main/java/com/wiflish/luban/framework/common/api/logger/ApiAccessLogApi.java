package com.wiflish.luban.framework.common.api.logger;

import com.wiflish.luban.framework.common.api.logger.dto.ApiAccessLogCreateReqDTO;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.List;

/**
 * API 访问日志的 API 接口
 *
 * @author wiflish
 */
public interface ApiAccessLogApi {

    /**
     * 创建 API 访问日志
     *
     * @param createDTO 创建信息
     */
    void createApiAccessLog(@Valid ApiAccessLogCreateReqDTO createDTO);

    List<String> getTopAccessUrls(Long userId, Collection<String> menuPaths, Integer top);
}
