package com.wiflish.luban.framework.common.api.logger;

import com.wiflish.luban.framework.common.api.logger.dto.ApiErrorLogCreateReqDTO;
import jakarta.validation.Valid;

/**
 * API 错误日志的 API 接口
 *
 * @author wiflish
 */
public interface ApiErrorLogApi {

    /**
     * 创建 API 错误日志
     *
     * @param createDTO 创建信息
     */
    void createApiErrorLog(@Valid ApiErrorLogCreateReqDTO createDTO);

}
