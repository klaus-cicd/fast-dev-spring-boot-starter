package com.klaus.fd.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * 缓存配置
 *
 * @author klaus
 * @date 2024/01/11
 */
@Slf4j
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                // .expireAfterAccess(10, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(3)
                // 缓存的最大条数
                .maximumSize(5));

        return caffeineCacheManager;
    }
}
