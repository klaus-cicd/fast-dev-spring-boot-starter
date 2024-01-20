package com.klaus.fd.util;

import cn.hutool.core.lang.Pair;
import com.klaus.fd.constant.DateConstant;
import com.klaus.fd.enums.TimeSpanEnum;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author Klaus
 */
public class DateUtil {

    /**
     * 获取当前UTC 毫秒级时间戳(13位)
     *
     * @return long
     */
    public static long nowTs() {
        return LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * 获取当前UTC时间
     *
     * @return LocalDateTime
     */
    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDateTime parse(String dateStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DateConstant.DEFAULT_DT_FORMAT));
    }

    /**
     * 将时间戳解析为UTC时间
     *
     * @param utcEpochMilli 毫秒级时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime parse(long utcEpochMilli) {
        return Instant.ofEpochMilli(utcEpochMilli).atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    /**
     * 将时间戳解析为对应时区的时间
     *
     * @param utcEpochMilli 毫秒级时间戳
     * @param zoneId        时区ID
     * @return LocalDateTime
     */
    public static LocalDateTime parse(long utcEpochMilli, ZoneId zoneId) {
        return Instant.ofEpochMilli(utcEpochMilli).atZone(zoneId).toLocalDateTime();
    }

    public static LocalDateTime parse(String dateStr, String format) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(format));
    }


    public static long toEpochSecond(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    public static LocalDateTime getDateBegin(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().atTime(LocalTime.MIDNIGHT);
    }


    public static LocalDateTime nextDate(LocalDateTime localDateTime) {
        return localDateTime.plusDays(1);
    }

    public static LocalDateTime tsToLocalDateTime(String utcEpochMilli) {
        return Instant.ofEpochMilli(Long.parseLong(utcEpochMilli)).atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public static LocalDate tsToLocalDate(long utcEpochMilli) {
        return Instant.ofEpochMilli(utcEpochMilli).atZone(ZoneOffset.UTC).toLocalDate();
    }

    public static Pair<Long, Long> getTsFrame(TimeSpanEnum timeSpanEnum, Long duration, LocalDateTime now) {
        LocalDateTime finalDateTime;
        switch (timeSpanEnum) {
            case HOUR:
                finalDateTime = now.minusHours(duration);
                break;
            case DAY:
                finalDateTime = now.minusDays(duration);
                break;
            case MINUTE:
            default:
                finalDateTime = now.minusMinutes(duration);
        }
        return Pair.of(DateUtil.toEpochMilli(finalDateTime), DateUtil.toEpochMilli(now));
    }
}
