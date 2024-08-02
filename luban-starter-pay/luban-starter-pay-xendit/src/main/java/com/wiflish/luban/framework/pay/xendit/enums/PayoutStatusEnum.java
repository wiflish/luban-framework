package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <a href="https://docs.xendit.co/api-payouts-beta/api-payouts-beta">New payout status</a>
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Getter
@AllArgsConstructor
public enum PayoutStatusEnum {
    ACCEPTED("ACCEPTED"),
    REQUESTED("REQUESTED"),
    FAILED("FAILED"),
    SUCCEEDED("SUCCEEDED"),
    CANCELLED("CANCELLED"),
    REVERSED("REVERSED"),
    ;

    private final String name;

    public static boolean isSuccess(String statusName) {
        return SUCCEEDED.getName().equals(statusName);
    }

    public static boolean isFailure(String statusName) {
        return FAILED.getName().equals(statusName);
    }
}
