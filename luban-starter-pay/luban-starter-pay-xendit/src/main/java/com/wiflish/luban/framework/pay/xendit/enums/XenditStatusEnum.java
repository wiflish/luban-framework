package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiflish
 * @since 2024-07-17
 */
@Getter
@AllArgsConstructor
public enum XenditStatusEnum {
    INVOICE_PENDING("PENDING"),
    INVOICE_PAID("PAID"),
    INVOICE_EXPIRED("EXPIRED"),
    ;

    private final String name;

    public static boolean isSuccess(String statusName) {
        return INVOICE_PAID.getName().equals(statusName);
    }
}
