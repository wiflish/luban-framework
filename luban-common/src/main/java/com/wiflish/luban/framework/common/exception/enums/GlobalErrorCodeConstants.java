package com.wiflish.luban.framework.common.exception.enums;

import com.wiflish.luban.framework.common.exception.ErrorCode;

/**
 * 全局错误码枚举
 * 0-999 系统异常编码保留
 *
 * 一般情况下，使用 HTTP 响应状态码 <a href="https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status">...</a>
 * 虽然说，HTTP 响应状态码作为业务使用表达能力偏弱，但是使用在系统层面还是非常不错的
 * 比较特殊的是，因为之前一直使用 0 作为成功，就不使用 200 啦。
 *
 * @author wiflish
 */
public interface GlobalErrorCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(0, "Success.");

    // ========== 客户端错误段 ==========

    ErrorCode BAD_REQUEST = new ErrorCode(400, "Request Parameters Error.");
    ErrorCode UNAUTHORIZED = new ErrorCode(401, "Account is not Login.");
    ErrorCode FORBIDDEN = new ErrorCode(403, "No Permission.");
    ErrorCode NOT_FOUND = new ErrorCode(404, "Not Found");
    ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "Request Method Error.");
    ErrorCode LOCKED = new ErrorCode(423, "Network Error, Please try again later."); // 并发请求，不允许
    ErrorCode TOO_MANY_REQUESTS = new ErrorCode(429, "Too frequent，Please try again later");

    // ========== 服务端错误段 ==========
    ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "Server Error.");
    ErrorCode NOT_IMPLEMENTED = new ErrorCode(501, "Feature not Implement. ");
    ErrorCode ERROR_CONFIGURATION = new ErrorCode(502, "Configuration Error.");

    // ========== 自定义错误段 ==========
    ErrorCode REPEATED_REQUESTS = new ErrorCode(900, "Repeated Request, Please try again later."); // 重复请求
    ErrorCode DEMO_DENY = new ErrorCode(901, "Demo Mode.");
    ErrorCode RECORD_NOT_FOUND = new ErrorCode(902, "Record Not Found.");
    ErrorCode DUPLICATE_KEY_ERROR = new ErrorCode(903, "Duplicate Key Error."); // 唯一键重复
    ErrorCode QUERY_DATE_RANG_ERROR = new ErrorCode(904, "查询日期范围不能超过{0}天");

    ErrorCode UNKNOWN = new ErrorCode(999, "Server Error.");

}
