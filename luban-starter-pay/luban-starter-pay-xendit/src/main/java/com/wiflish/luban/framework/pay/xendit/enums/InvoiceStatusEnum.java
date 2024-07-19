package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <a href="https://docs.xendit.co/payment-link/statuses">Payment Link Statuses</a>
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Getter
@AllArgsConstructor
public enum InvoiceStatusEnum {
    PENDING("PENDING"),
    PAID("PAID"),
    EXPIRED("EXPIRED"),
    ;

    private final String name;

    public static boolean isSuccess(String statusName) {
        return PAID.getName().equals(statusName);
    }
}
