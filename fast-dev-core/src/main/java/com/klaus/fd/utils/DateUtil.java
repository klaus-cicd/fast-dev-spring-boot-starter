package com.klaus.fd.utils;

import com.klaus.fd.constant.DateConstant;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 日期工具
 *
 * @author klaus
 * @date 2024/01/02
 */
public class DateUtil {

    public static LocalDateTime now() {
        return LocalDateTime.now(DateConstant.ZONE_ID_SHANGHAI);
    }

    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }


    public static long nowTs() {
        return now().toInstant(ZoneOffset.of("Asia/Shanghai")).toEpochMilli();
    }

    public static long nowUtcTs() {
        return nowUtc().toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
