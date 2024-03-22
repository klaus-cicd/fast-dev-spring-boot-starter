package com.klaus.fd.lock.provider;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author Klaus
 */
public final class LockMethodProvider {
    public static final ConcurrentMap<String, Method> METHOD_CONCURRENT_MAP = new ConcurrentHashMap<>();
    private static final LockMethodProvider LOCK_METHOD_PROVIDER = new LockMethodProvider();


    private LockMethodProvider() {

    }

    public static LockMethodProvider instance() {
        return LOCK_METHOD_PROVIDER;
    }


    public Method getMethod(Class<?> clz, String methodName, Class<?>[] args) {
        String clzName = clz.getName();
        String key = StrUtil.join(":", clzName, methodName);
        Method method = METHOD_CONCURRENT_MAP.get(key);
        if (method == null) {
            method = BeanUtils.findMethod(clz, methodName, args);
            if (method != null) {
                METHOD_CONCURRENT_MAP.put(key, method);
            }
        }
        return method;
    }
}
