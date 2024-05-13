package com.klaus.fd.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * 使用Redis限制输出频率的日志工具类
 *
 * @author Klaus
 */
@Slf4j
public class LogLimitedUtil {

    public static void info(String logKey, String msg, int limitMinute, Object... args) {
        Boolean exists = RedisUtil.exists(logKey);
        if (Boolean.FALSE.equals(exists) && log.isInfoEnabled()) {
            log.info(msg, args);
            RedisUtil.set(logKey, "", Duration.ofMinutes(limitMinute));
        }
    }

    public static void debug(String logKey, String msg, int limitMinute, Object... args) {
        Boolean exists = RedisUtil.exists(logKey);
        if (Boolean.FALSE.equals(exists) && log.isDebugEnabled()) {
            log.debug(msg, args);
            RedisUtil.set(logKey, "", Duration.ofMinutes(limitMinute));
        }
    }

    public static void warn(String logKey, String msg, int limitMinute, Object... args) {
        Boolean exists = RedisUtil.exists(logKey);
        if (Boolean.FALSE.equals(exists) && log.isWarnEnabled()) {
            log.warn(msg, args);
            RedisUtil.set(logKey, "", Duration.ofMinutes(limitMinute));
        }
    }

    public static void error(String logKey, String msg, int limitMinute, Throwable e, Object... args) {
        Boolean exists = RedisUtil.exists(logKey);
        if (Boolean.FALSE.equals(exists)) {
            if (e == null) {
                log.error(msg, args);
            } else {
                log.error(msg, args, e);
            }
            RedisUtil.set(logKey, "", Duration.ofMinutes(limitMinute));
        }
    }
}
