package com.wiflish.luban.framework.common.api.logger;

import com.wiflish.luban.framework.common.api.logger.dto.LoginLogCreateReqDTO;
import jakarta.validation.Valid;

/**
 * 登录日志的 API 接口
 *
 * @author wiflish
 */
public interface LoginLogApi {

    /**
     * 创建登录日志
     *
     * @param reqDTO 日志信息
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
