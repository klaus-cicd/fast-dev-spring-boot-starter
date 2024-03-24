package com.klaus.fd.util;

import cn.hutool.extra.cglib.CglibUtil;
import com.klaus.fd.utils.BeanUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Redis工具类
 *
 * @author Klaus
 */

public class RedisCache {

    private static final StringRedisTemplate STRING_REDIS_TEMPLATE = BeanUtil.getBean(StringRedisTemplate.class);
    private static final RedisTemplate<String, Object> REDIS_TEMPLATE = BeanUtil.getBean(RedisTemplate.class);

    public static String get(String key) {
        checkBean();
        return STRING_REDIS_TEMPLATE.opsForValue().get(key);
    }

    public static void set(String key, String value) {
        checkBean();
        STRING_REDIS_TEMPLATE.opsForValue().set(key, value);
    }

    public static void set(String key, String value, Duration duration) {
        checkBean();
        STRING_REDIS_TEMPLATE.opsForValue().set(key, value, duration);
    }

    public static Boolean del(String key) {
        checkBean();
        return STRING_REDIS_TEMPLATE.delete(key);
    }

    public static void setList(String key, Collection<Object> list) {
        checkBean();
        REDIS_TEMPLATE.opsForList().leftPushAll(key, list);
    }

    public static void addList(String key, Object item) {
        checkBean();
        REDIS_TEMPLATE.opsForList().leftPush(key, item);
    }

    public static <T> List<T> getList(String key, Supplier<T> supplier) {
        List<Object> allList = doGetList(key);
        if (CollectionUtils.isEmpty(allList)) {
            return Collections.emptyList();
        }
        return CglibUtil.copyList(allList, supplier);
    }

    public static List<String> getStrList(String key) {
        List<Object> allList = doGetList(key);
        if (CollectionUtils.isEmpty(allList)) {
            return Collections.emptyList();
        }
        return allList.stream().map(String::valueOf).collect(Collectors.toList());
    }

    public static List<Integer> getIntList(String key) {
        List<Object> allList = doGetList(key);
        if (CollectionUtils.isEmpty(allList)) {
            return Collections.emptyList();
        }
        return allList.stream().map(item -> (int) item).collect(Collectors.toList());
    }

    private static List<Object> doGetList(String key) {
        checkBean();
        return REDIS_TEMPLATE.opsForList().range(key, 0, -1);
    }

    public static List<Long> getLongList(String key) {
        List<Object> allList = doGetList(key);
        if (CollectionUtils.isEmpty(allList)) {
            return Collections.emptyList();
        }
        return allList.stream().map(item -> (long) item).collect(Collectors.toList());
    }

    public static <T> T getListItem(String key, Integer index, Class<T> targetClass) {
        checkBean();
        Object object = REDIS_TEMPLATE.opsForList().index(key, index);
        if (object == null) {
            return null;
        }
        return CglibUtil.copy(object, targetClass);
    }

    public static <T> T hGet(String key, Class<T> targetClass) {
        // TODO RedisCache.hGet
        return null;
    }

    public static void checkBean() {
        if (STRING_REDIS_TEMPLATE == null || REDIS_TEMPLATE == null) {
            throw new RuntimeException();
        }
    }

}
