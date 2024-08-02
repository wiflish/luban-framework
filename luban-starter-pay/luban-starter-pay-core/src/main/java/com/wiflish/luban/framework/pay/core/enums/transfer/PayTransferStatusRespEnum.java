package com.wiflish.luban.framework.pay.core.enums.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 渠道的转账状态枚举
 *
 * @author jason
 */
@Getter
@AllArgsConstructor
public enum PayTransferStatusRespEnum {

    WAITING(0, "等待转账"),

    IN_PROGRESS(10, "转账进行中"),

    SUCCESS(20, "转账成功"),
    /**
     * 转账关闭 (失败，或者其它情况)
     */
    CLOSED(30, "转账关闭");

    private final Integer status;
    private final String name;

    public static boolean isSuccess(Integer status) {
        return Objects.equals(status, SUCCESS.getStatus());
    }

    public static boolean isClosed(Integer status) {
        return Objects.equals(status, CLOSED.getStatus());
    }

    public static boolean isInProgress(Integer status) {
        return Objects.equals(status, IN_PROGRESS.getStatus());
    }
}
