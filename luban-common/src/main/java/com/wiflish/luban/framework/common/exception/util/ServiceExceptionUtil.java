package com.wiflish.luban.framework.common.exception.util;

import com.wiflish.luban.framework.common.exception.ErrorCode;
import com.wiflish.luban.framework.common.exception.ServiceException;
import com.wiflish.luban.framework.common.exception.enums.GlobalErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link ServiceException} 工具类
 * 1. 异常提示信息，写在 .properties 等等配置文件
 * 2. 异常提示信息，写在 Apollo 等等配置中心中，从而实现可动态刷新
 * 3. 异常提示信息，存储在 db 等等数据库中，从而实现可动态刷新
 */
@Slf4j
public class ServiceExceptionUtil {
    public static ServiceException exception(ErrorCode errorCode) {
        return new ServiceException(errorCode);
    }

    public static ServiceException exception(ErrorCode errorCode, Object... params) {
        if (params == null || params.length == 0) {
            return new ServiceException(errorCode);
        }
        String[] strParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            strParams[i] = String.valueOf(params[i]);
        }
        return new ServiceException(errorCode, strParams);
    }

    public static ServiceException exception0(Integer code, String messagePattern, Object... params) {
        if (params == null || params.length == 0) {
            return new ServiceException(code, messagePattern);
        }
        String[] strParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            strParams[i] = String.valueOf(params[i]);
        }
        return new ServiceException(code, messagePattern, strParams);
    }

    public static ServiceException invalidParamException(String messagePattern, Object... params) {
        return exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), messagePattern, params);
    }
}
