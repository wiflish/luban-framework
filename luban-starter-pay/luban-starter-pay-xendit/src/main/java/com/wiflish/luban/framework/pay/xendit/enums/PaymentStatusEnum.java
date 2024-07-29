package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Getter
@AllArgsConstructor
public enum PaymentStatusEnum {
    REQUIRES_ACTION("REQUIRES_ACTION"),
    PENDING("PENDING"),
    FAILED("FAILED"),
    SUCCEEDED("SUCCEEDED"),
    AWAITING_CAPTURE("AWAITING_CAPTURE"),
    ;

    private final String name;

    public static boolean isSuccess(String statusName) {
        return SUCCEEDED.getName().equals(statusName);
    }
}
