package com.wiflish.luban.framework.pay.ezeelink.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiflish
 * @since 2024-08-09
 */
@Getter
@AllArgsConstructor
public enum EzeelinkOVOLookupAccountStatusEnum {
    OPEN("open", "account will need to be binded"),
    RECORDED("recorded", "account is ready for direct debit"),
    PROCESSING("processing", "is currently being binded"),
    UNUSABLE("unusable", "OVO account not allowed to be used"),
    ;

    private final String status;
    private final String desc;

    public static boolean isOpen(String status) {
        return OPEN.status.equals(status);
    }

    public static boolean isProcessing(String status) {
        return PROCESSING.status.equals(status);
    }

    public static boolean isUnusable(String status) {
        return UNUSABLE.status.equals(status);
    }
}
