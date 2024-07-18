package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiflish
 * @since 2024-07-17
 */
@Getter
@AllArgsConstructor
public enum XenditHeaderEnum {
    CALLBACK_TOKEN("x-callback-token"),
    ;
    private final String header;
}
