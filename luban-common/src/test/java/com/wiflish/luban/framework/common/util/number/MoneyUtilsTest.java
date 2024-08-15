package com.wiflish.luban.framework.common.util.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wiflish
 * @since 2024-08-05
 */
class MoneyUtilsTest {

    @Test
    void calculateRatePrice() {

        Long price = 1999999L;
        Integer rate = 1152;

        Long ratePrice = MoneyUtils.calculateRatePrice(price, rate, "IDR");
        assertEquals(230300L, ratePrice);

        ratePrice = MoneyUtils.calculateRatePrice(price, rate, "CNY");
        assertEquals(23039988L, ratePrice);

        ratePrice = MoneyUtils.calculateRatePrice(100L, 1);
        assertEquals(0, ratePrice);

        ratePrice = MoneyUtils.calculateRatePrice(10000L, 1);
        assertEquals(1, ratePrice);

        ratePrice = MoneyUtils.calculateRatePrice(1000L, 123);
        assertEquals(12, ratePrice);

        ratePrice = MoneyUtils.calculateRatePrice(1000L, 125);
        assertEquals(13, ratePrice);
    }

    @Test
    void calculatePrice() {
        Long price = 1999999L;
        assertEquals(1999999L, MoneyUtils.calculatePrice(price, "IDR"));
        assertEquals(19999.99, MoneyUtils.calculatePrice(price, "CNY"));

    }
}