package com.wiflish.luban.framework.common.util.date;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * 时间工具类，用于 {@link java.time.LocalDateTime}
 *
 * @author wiflish
 */
public class LocalDateTimeUtils {

    /**
     * 空的 LocalDateTime 对象，主要用于 DB 唯一索引的默认值
     */
    public static LocalDateTime EMPTY = buildTime(1970, 1, 1);

    public static LocalDateTime addTime(Duration duration) {
        return LocalDateTime.now().plus(duration);
    }

    public static LocalDateTime minusTime(Duration duration) {
        return LocalDateTime.now().minus(duration);
    }

    public static boolean beforeNow(LocalDateTime date) {
        return date.isBefore(LocalDateTime.now());
    }

    public static boolean afterNow(LocalDateTime date) {
        return date.isAfter(LocalDateTime.now());
    }

    /**
     * 创建指定时间
     *
     * @param year  年
     * @param mouth 月
     * @param day   日
     * @return 指定时间
     */
    public static LocalDateTime buildTime(int year, int mouth, int day) {
        return LocalDateTime.of(year, mouth, day, 0, 0, 0);
    }

    public static LocalDateTime[] buildBetweenTime(int year1, int mouth1, int day1,
                                                   int year2, int mouth2, int day2) {
        return new LocalDateTime[]{buildTime(year1, mouth1, day1), buildTime(year2, mouth2, day2)};
    }

    /**
     * 判断当前时间是否在该时间范围内
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 是否
     */
    public static boolean isBetween(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
        return LocalDateTimeUtil.isIn(LocalDateTime.now(), startTime, endTime);
    }

    /**
     * 判断当前时间是否在该时间范围内
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 是否
     */
    public static boolean isBetween(String startTime, String endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
        LocalDate nowDate = LocalDate.now();
        return LocalDateTimeUtil.isIn(LocalDateTime.now(),
                LocalDateTime.of(nowDate, LocalTime.parse(startTime)),
                LocalDateTime.of(nowDate, LocalTime.parse(endTime)));
    }

    /**
     * 判断时间段是否重叠
     *
     * @param startTime1 开始 time1
     * @param endTime1   结束 time1
     * @param startTime2 开始 time2
     * @param endTime2   结束 time2
     * @return 重叠：true 不重叠：false
     */
    public static boolean isOverlap(LocalTime startTime1, LocalTime endTime1, LocalTime startTime2, LocalTime endTime2) {
        LocalDate nowDate = LocalDate.now();
        return LocalDateTimeUtil.isOverlap(LocalDateTime.of(nowDate, startTime1), LocalDateTime.of(nowDate, endTime1),
                LocalDateTime.of(nowDate, startTime2), LocalDateTime.of(nowDate, endTime2));
    }

    /**
     * 获取指定日期所在的月份的开始时间
     * 例如：2023-09-30 00:00:00,000
     *
     * @param date 日期
     * @return 月份的开始时间
     */
    public static LocalDateTime beginOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 获取指定日期所在的月份的最后时间
     * 例如：2023-09-30 23:59:59,999
     *
     * @param date 日期
     * @return 月份的结束时间
     */
    public static LocalDateTime endOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    /**
     * 获取指定日期到现在过了几天，如果指定日期在当前日期之后，获取结果为负
     *
     * @param dateTime 日期
     * @return 相差天数
     */
    public static Long between(LocalDateTime dateTime) {
        return LocalDateTimeUtil.between(dateTime, LocalDateTime.now(), ChronoUnit.DAYS);
    }

    /**
     * 获取今天的开始时间
     *
     * @return 今天
     */
    public static LocalDateTime getToday() {
        return LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
    }

    /**
     * 获取昨天的开始时间
     *
     * @return 昨天
     */
    public static LocalDateTime getYesterday() {
        return LocalDateTimeUtil.beginOfDay(LocalDateTime.now().minusDays(1));
    }

    /**
     * 获取明天的开始时间
     *
     * @return 昨天
     */
    public static LocalDateTime getTomorrow() {
        return LocalDateTimeUtil.beginOfDay(LocalDateTime.now().plusDays(1));
    }

    /**
     * 获取本月的开始时间
     *
     * @return 本月
     */
    public static LocalDateTime getMonth() {
        return beginOfMonth(LocalDateTime.now());
    }

    /**
     * 获取本年的开始时间
     *
     * @return 本年
     */
    public static LocalDateTime getYear() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    /**
     * 获取今天开始到明天0点的今天时间范围。
     *
     * @return 时间范围
     */
    public static LocalDateTime[] getTodayRange() {
        return new LocalDateTime[]{getToday(), getTomorrow()};
    }

    public static LocalDateTime beginOfDay(LocalDateTime time) {
        return time.with(LocalTime.MIN);
    }

    public static LocalDateTime endOfDay(LocalDateTime time) {
        return endOfDay(time, false);
    }

    public static LocalDateTime endOfDay(LocalDateTime time, boolean truncateMillisecond) {
        if (truncateMillisecond) {
            return time.with(LocalTime.of(23, 59, 59));
        }
        //MySQL不支持999999999，只支持6个999999。
        return time.with(LocalTime.of(23, 59, 59, 999999000));
    }

    /**
     * 标准时区转化为北京时间.
     *
     * @param gmtTime gmt时间
     * @return 北京时间
     */
    public static LocalDateTime gmt2Beijing(String gmtTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(gmtTime, DateTimeFormatter.ISO_DATE_TIME);
        ZonedDateTime beijingTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));

        return beijingTime.toLocalDateTime();
    }

    /**
     * 北京时间转化为标准时区
     *
     * @param currentDateTime 北京时间
     * @return gmt时间
     */
    public static String beijing2GmtString(LocalDateTime currentDateTime) {
        if (currentDateTime == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.of(currentDateTime, ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm:ss.")
                .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                .appendOffset("+HH:mm", "Z")
                .toFormatter();

        return zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).format(formatter);
    }

    /**
     * 北京时间转化为ISO8601
     *
     * @param currentDateTime 北京时间
     * @return gmt时间
     */
    public static String beijing2ISO8601(LocalDateTime currentDateTime) {
        if (currentDateTime == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.of(currentDateTime, ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx");

        return zonedDateTime.format(formatter);
    }

    public static LocalDateTime getNow() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
