package com.klaus.fd.lock;

import com.klaus.fd.lock.aop.LockAspect;
import com.klaus.fd.lock.entities.LockSettings;
import com.klaus.fd.lock.enums.LockProvider;
import com.klaus.fd.lock.expections.LockException;
import com.klaus.fd.lock.impl.LockDelegateImpl;
import com.klaus.fd.lock.impl.local.LocalLockExecutor;
import com.klaus.fd.lock.impl.redisson.RedissonHandlerImpl;
import com.klaus.fd.lock.impl.redisson.RedissonLockExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author Klaus
 */
@Slf4j
public class LockConfig {

    @Value("${lock.redisson.host:127.0.0.1}")
    private String host;
    @Value("${lock.redisson.port:6379}")
    private String port;
    @Value("${lock.redisson.password:}")
    private String pwd;
    @Value("${lock.lockFailThrowEnabled:true}")
    private String lockFailThrowEnabled;
    @Value("${lock.provider:redis}")
    private String lockProviderStr;
    @Value("${lock.cache:true}")
    private String lockCacheEnabled;


    @Bean
    public LockDelegate lockDelegate(LockSettings lockSettings, LockExecutor lockExecutor) {
        return new LockDelegateImpl(lockSettings, lockExecutor);
    }

    /**
     * 创建Lock注解AOP配置类
     *
     * @return LockAspect.class
     */
    @Bean
    public LockAspect lockAspect(LockDelegate lockDelegate) {
        return new LockAspect(lockDelegate);
    }


    @Bean
    @ConditionalOnMissingBean(LockSettings.class)
    public LockSettings lockSettings() {
        LockProvider lockProvider = LockProvider.match(lockProviderStr.toUpperCase());
        if (null == lockProvider) {
            throw new LockException("Create LockSettings error, there is not support lock provider:" + lockProviderStr);
        }

        LockSettings lockSettings = LockSettings.builder()
                .provider(lockProvider)
                .server(host)
                .port(Integer.parseInt(port))
                .password(pwd)
                .throwExceptionOnLockFailed(Boolean.TRUE.toString().equalsIgnoreCase(lockFailThrowEnabled))
                .build();

        log.info("Create bean successful: LockSettings.class");
        return lockSettings;
    }

    @Bean
    @ConditionalOnBean(LockSettings.class)
    public LockExecutor lockExecutor(LockSettings lockSettings) {
        boolean cacheEnabled = Boolean.TRUE.toString().equalsIgnoreCase(lockCacheEnabled);
        switch (lockSettings.getProvider()) {
            case LOCAL:
                return new LocalLockExecutor(cacheEnabled);
            case REDIS:
                return new RedissonLockExecutor(new RedissonHandlerImpl(lockSettings), cacheEnabled);
            default:
                throw new LockException(" @EnableLock can't support " + lockSettings.getProvider());
        }
    }

}
