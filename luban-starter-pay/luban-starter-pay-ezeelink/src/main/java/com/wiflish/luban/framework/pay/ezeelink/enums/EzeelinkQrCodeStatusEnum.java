package com.wiflish.luban.framework.pay.ezeelink.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Getter
@AllArgsConstructor
public enum EzeelinkQrCodeStatusEnum {
    SUCCESS("2", "Success/Paid"),
    WAITING("5", "Waiting for payment"),
    EXPIRED("6", "Payment Expired"),
    ;

    private final String status;
    private final String desc;

    public static boolean isSuccess(String statusName) {
        return SUCCESS.status.equals(statusName);
    }
}
