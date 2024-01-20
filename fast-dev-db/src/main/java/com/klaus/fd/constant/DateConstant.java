package com.klaus.fd.constant;

import java.time.ZoneId;

/**
 * @author Klaus
 */
public class DateConstant {


    /**
     * 精确到秒
     * eg. 202012121313
     */
    public static final String DEFAULT_DT_FORMAT = "yyyyMMddHHmmss";

    /**
     * 精确到天
     * eg. 20201212
     */
    public static final String DEFAULT_D_FORMAT = "yyyyMMdd";

    /**
     * 精确到时
     * eg.2022121213
     */
    public static final String DH_FORMAT = "yyyyMMddHH";

    /**
     * 精确到月
     * eg. 202012
     */
    public static final String DEFAULT_M_FORMAT = "yyyyMM";

    public static final String PATTERN_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final ZoneId ZONE_ID_UTC = ZoneId.of("UTC");
    public static final ZoneId ZONE_ID_SHANGHAI = ZoneId.of("Asia/Shanghai");
}
