package com.wiflish.luban.framework.pay.ezeelink.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiflish
 * @since 2024-07-17
 */
@Getter
@AllArgsConstructor
public enum EzeelinkHeaderEnum {
    API_KEY("EZ_Key"),
    API_SECRET("EZ_Secret"),
    API_TIMESTAMP("EZ_Timestamp"),
    API_SIGNATURE("EZ_Signature"),
    ;
    private final String header;
}
