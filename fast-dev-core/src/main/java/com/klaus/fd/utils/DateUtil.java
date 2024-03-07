package com.klaus.fd.utils;

import com.klaus.fd.constant.DateConstant;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具
 *
 * @author klaus
 * @date 2024/01/02
 */
public class DateUtil {
    public static final ZoneId DEFAULT_ZONE_ID = DateConstant.ZONE_ID_SHANGHAI;
    public static final ZoneOffset DEFAULT_ZONE_OFFSET = DateConstant.ZONE_OFFSET_SHANGHAI;

    public static LocalDateTime now() {
        return LocalDateTime.now(DEFAULT_ZONE_ID);
    }

    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }


    public static long nowTs() {
        return now().toInstant(DEFAULT_ZONE_OFFSET).toEpochMilli();
    }

    public static long nowUtcTs() {
        return nowUtc().toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static long toLong(Timestamp timestamp) {
        return timestamp.getTime();
    }

    public static LocalDate parseToLocalDate(long millTs) {
        return Instant.ofEpochMilli(millTs).atZone(DEFAULT_ZONE_ID).toLocalDate();
    }

    /**
     * 将时间戳解析为UTC时间
     *
     * @param millTs 毫秒级时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime parse(long millTs) {
        return Instant.ofEpochMilli(millTs).atZone(DEFAULT_ZONE_ID).toLocalDateTime();
    }

    /**
     * 将时间戳解析为对应时区的时间
     *
     * @param millTs 毫秒级时间戳
     * @param zoneId 时区ID
     * @return LocalDateTime
     */
    public static LocalDateTime parse(long millTs, ZoneId zoneId) {
        return Instant.ofEpochMilli(millTs).atZone(zoneId).toLocalDateTime();
    }

    public static LocalDateTime parse(String dateStr, String format) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(format));
    }

    public static Timestamp parseToTimestamp(long longValue) {
        return new Timestamp(longValue);
    }
}
