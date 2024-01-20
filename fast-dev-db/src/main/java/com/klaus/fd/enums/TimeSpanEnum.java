package com.klaus.fd.enums;

import lombok.Getter;

/**
 * @author Silas
 */
@Getter
public enum TimeSpanEnum {

    /**
     * 分钟
     */
    MINUTE,
    /**
     * 小时
     */
    HOUR,
    /**
     * 日
     */
    DAY;

    public static TimeSpanEnum match(String name) {
        for (TimeSpanEnum timeSpanEnum : values()) {
            if (timeSpanEnum.name().equalsIgnoreCase(name)) {
                return timeSpanEnum;
            }
        }
        return null;
    }


}