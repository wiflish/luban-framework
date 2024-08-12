package com.wiflish.luban.framework.common.util.number;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author wiflish
 * @since 2024-08-12
 */
@Getter
@AllArgsConstructor
public enum CurrencyEnum {
    CNY("CNY", 2),
    IDR("IDR", 0),
    ;

    private final String code;
    private final Integer decimal;

    public static CurrencyEnum getByCode(String code) {
        return Arrays.stream(CurrencyEnum.values()).filter(currencyEnum -> currencyEnum.getCode().equals(code)).findFirst().orElse(CNY);
    }
}
