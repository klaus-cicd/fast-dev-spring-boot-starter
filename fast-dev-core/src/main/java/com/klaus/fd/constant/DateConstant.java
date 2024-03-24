package com.klaus.fd.constant;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


/**
 * 日期常量
 *
 * @author klaus
 * @date 2024/01/04
 */
public class DateConstant {


    /**
     * 精确到秒
     * eg. 202012121313
     */
    public static final String S_FORMAT = "yyyyMMddHHmmss";

    /**
     * 精确到天
     * eg. 20201212
     */
    public static final String D_FORMAT = "yyyyMMdd";

    /**
     * 精确到月
     * eg. 202012
     */
    public static final String M_FORMAT = "yyyyMM";

    public static final String PATTERN_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String UTC_RFC3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SHANGHAI_RFC3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'+08:00'";
    public static final String DF_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final ZoneId ZONE_ID_UTC = ZoneOffset.UTC;
    public static final String ASIA_SHANGHAI = "Asia/Shanghai";
    public static final ZoneId ZONE_ID_SHANGHAI = ZoneId.of(ASIA_SHANGHAI);
    public static final ZoneOffset ZONE_OFFSET_SHANGHAI = ZoneOffset.of("+08:00");
    public static final DateTimeFormatter DTF_SHANGHAI = DateTimeFormatter.ofPattern(SHANGHAI_RFC3339_FORMAT)
            .withZone(ZONE_ID_SHANGHAI);
}
