package com.klaus.fd.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 缓存配置
 *
 * @author klaus
 * @date 2024/01/11
 */
@Slf4j
public class CacheAutoConfiguration {

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

    @Bean
    @Primary
    @ConditionalOnMissingClass
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Value值采用自定义的Jackson2JsonRedisSerializer序列化方式
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        template.setHashValueSerializer(serializer);
        template.setValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}
