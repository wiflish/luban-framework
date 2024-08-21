package com.wiflish.luban.framework.pay.ezeelink.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiflish
 * @since 2024-08-21
 */
@Getter
@AllArgsConstructor
public enum EzeelinkAccountStatusEnum {
    SUCCESS("Success", "Success/Paid"),
    FAILED("Failed", "Failed"),
    PENDING("Pending", "Pending"),
    ;

    private final String status;
    private final String desc;

    public static boolean isSuccess(String statusName) {
        return SUCCESS.status.equals(statusName);
    }
}
