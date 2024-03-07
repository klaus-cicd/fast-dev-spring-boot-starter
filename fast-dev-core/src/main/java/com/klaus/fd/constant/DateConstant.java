package com.klaus.fd.constant;

import java.time.ZoneId;
import java.time.ZoneOffset;


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
    public static final ZoneId ZONE_ID_UTC = ZoneOffset.UTC;
    public static final String TIMEZONE = "Asia/Shanghai";
    public static final ZoneId ZONE_ID_SHANGHAI = ZoneId.of(TIMEZONE);
    public static final ZoneOffset ZONE_OFFSET_SHANGHAI = ZoneOffset.of("+08:00");
}
