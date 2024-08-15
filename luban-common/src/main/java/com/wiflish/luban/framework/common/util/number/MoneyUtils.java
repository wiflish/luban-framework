package com.wiflish.luban.framework.common.util.number;

import cn.hutool.core.math.Money;
import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额工具类
 *
 * @author wiflish
 */
public class MoneyUtils {

    /**
     * 金额的小数位数
     */
    private static final int PRICE_SCALE = 2;

    /**
     * 百分比对应的 BigDecimal 对象
     */
    public static final BigDecimal PERCENT_100 = BigDecimal.valueOf(100);

    /**
     * 计算百分比金额，根据货币单位做取舍
     *
     * @param price 金额, 单位为分的金额.
     * @param rate  百分比，例如说 56.77% 则传入 5677
     * @return 百分比金额
     */
    public static Long calculateRatePrice(Long price, Integer rate, String currencyCode) {
        CurrencyEnum currencyEnum = CurrencyEnum.getByCode(currencyCode);
        if (currencyEnum.getDecimal() == 0) {
            return calculateRatePrice(price, rate / 100.00, 0, RoundingMode.DOWN).longValue();
        }
        // 有小数点
        return calculateRatePrice(price / 100, rate, 0, RoundingMode.HALF_UP).longValue();
    }

    /**
     * 根据货币单位计算金额
     *
     * @param price        金额
     * @param currencyCode 货币单位
     * @return 实际金额
     */
    public static Number calculatePrice(Long price, String currencyCode) {
        CurrencyEnum currencyEnum = CurrencyEnum.getByCode(currencyCode);
        // 货币无小数点
        if (currencyEnum.getDecimal() == 0) {
            return price;
        }
        return price / 100.00;
    }

    /**
     * 计算百分比金额，四舍五入
     *
     * @param price 金额
     * @param rate  百分比，例如说 56.77% 则传入 56.77
     * @return 百分比金额
     */
    public static Long calculateRatePrice(Long price, Double rate) {
        return calculateRatePrice(price, rate, 0, RoundingMode.HALF_UP).longValue();
    }

    /**
     * 计算百分比金额，四舍五入
     *
     * @param price 金额
     * @param rate  百分比，例如说 56.77% 则传入 5677
     * @return 百分比金额
     */
    public static Long calculateRatePrice(Long price, Integer rate) {
        return calculateRatePrice(price, rate / 100.00, 0, RoundingMode.HALF_UP).longValue();
    }

    /**
     * 计算百分比金额，向下传入
     *
     * @param price 金额
     * @param rate  百分比，例如说 56.77% 则传入 56.77
     * @return 百分比金额
     */
    public static Long calculateRatePriceFloor(Long price, Double rate) {
        return calculateRatePrice(price, rate, 0, RoundingMode.FLOOR).longValue();
    }

    /**
     * 计算百分比金额
     *
     * @param price   金额（单位分）
     * @param count   数量
     * @param percent 折扣（单位分），列如 60.2%，则传入 6020
     * @return 商品总价
     */
    public static Long calculator(Long price, Integer count, Integer percent) {
        price = price * count;
        if (percent == null) {
            return price;
        }
        return MoneyUtils.calculateRatePriceFloor(price, (double) (percent / 100));
    }

    /**
     * 计算百分比金额
     *
     * @param price        金额
     * @param rate         百分比，例如说 56.77% 则传入 56.77
     * @param scale        保留小数位数
     * @param roundingMode 舍入模式
     */
    public static BigDecimal calculateRatePrice(Number price, Number rate, int scale, RoundingMode roundingMode) {
        return NumberUtil.toBigDecimal(price).multiply(NumberUtil.toBigDecimal(rate)) // 乘以
                .divide(BigDecimal.valueOf(100), scale, roundingMode); // 除以 100
    }

    /**
     * 分转元
     *
     * @param fen 分
     * @return 元
     */
    public static BigDecimal fenToYuan(int fen) {
        return new Money(0, fen).getAmount();
    }

    /**
     * 分转元（字符串）
     *
     * 例如说 fen 为 1 时，则结果为 0.01
     *
     * @param fen 分
     * @return 元
     */
    public static String fenToYuanStr(int fen) {
        return new Money(0, fen).toString();
    }

    /**
     * 分转元（字符串）
     * <p>
     * 例如说 fen 为 1 时，则结果为 0.01
     *
     * @param fen 分
     * @return 元
     */
    public static String fenToYuanStr(long fen) {
        return fenToYuanStr((int) fen);
    }

    /**
     * 金额相乘，默认进行四舍五入
     *
     * 位数：{@link #PRICE_SCALE}
     *
     * @param price 金额
     * @param count 数量
     * @return 金额相乘结果
     */
    public static BigDecimal priceMultiply(BigDecimal price, BigDecimal count) {
        if (price == null || count == null) {
            return null;
        }
        return price.multiply(count).setScale(PRICE_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 金额相乘（百分比），默认进行四舍五入
     *
     * 位数：{@link #PRICE_SCALE}
     *
     * @param price  金额
     * @param percent 百分比
     * @return 金额相乘结果
     */
    public static BigDecimal priceMultiplyPercent(BigDecimal price, BigDecimal percent) {
        if (price == null || percent == null) {
            return null;
        }
        return price.multiply(percent).divide(PERCENT_100, PRICE_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 计算优惠金额，优惠金额不能超过总金额，也不能超过最大优惠金额
     *
     * @param totalPrice         总金额
     * @param discountPrice      折扣金额
     * @param discountLimitPrice 折扣最大优惠
     * @return 优惠金额
     */
    public static Long calculateDiscountPrice(Long totalPrice, Long discountPrice, Long discountLimitPrice) {
        // 优惠金额不能超过总金额
        long min = Math.min(totalPrice, discountPrice);
        // 有优惠最大限额时，不能超过最大限额
        if (discountLimitPrice != null && discountLimitPrice > 0) {
            return Math.min(min, discountLimitPrice);
        }
        return min;
    }
}
