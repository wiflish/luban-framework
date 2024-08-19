package com.wiflish.luban.framework.common.util.string;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 国家电话编码枚举.
 *
 * @author wiflish
 * @since 2024-08-19
 */
@Getter
@AllArgsConstructor
public enum PhoneEnum {
    CN("CN", "86", ""),
    ID("ID", "62", "0"),
    ;
    private final String stateCode;
    private final String externalPhonePrefix;
    private final String internalPhonePrefix;

    public static PhoneEnum getByStateCode(String stateCode) {
        return Arrays.stream(com.wiflish.luban.framework.common.util.string.PhoneEnum.values())
                .filter(item -> item.stateCode.equals(stateCode))
                .findFirst()
                .orElse(CN);
    }

    public static PhoneEnum getByExternalPhonePrefix(String externalPhonePrefix) {
        return Arrays.stream(PhoneEnum.values())
                .filter(item -> item.externalPhonePrefix.equals(externalPhonePrefix))
                .findFirst()
                .orElse(CN);
    }
}

