package com.klaus.fd.util;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;

import javax.annotation.Resource;


@CacheConfig(cacheManager = "cacheManager")
public class CacheUtil {

    @Resource
    private CacheManager cacheManager;

    public Cache getCaffeineCache() {
        return cacheManager.getCache("caffeine");
    }

    public String getStr(String key) {
        return getCaffeineCache().get(key, String.class);
    }


    public void add(String key, String value) {
        getCaffeineCache().put(key, value);
    }

    public void add(String key, Object object) {
        getCaffeineCache().put(key, object);
    }

    public <T> T get(String key, Class<T> targetClass) {
        return getCaffeineCache().get(key, targetClass);
    }
}
