package com.klaus.fd.lock.impl.redisson;

import cn.hutool.core.util.StrUtil;
import com.klaus.fd.lock.Lock;
import com.klaus.fd.lock.LockExecutor;
import com.klaus.fd.lock.RedissonHandler;
import com.klaus.fd.lock.entities.LockEntity;
import com.klaus.fd.lock.enums.LockType;
import com.klaus.fd.lock.expections.LockException;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author Klaus
 */
public class RedissonLockExecutor implements LockExecutor {

    private final RedissonHandler redissonHandler;

    /**
     * 是否将锁存入Map, 自制锁池
     * 高并发时推荐开启(空间换时间思想)
     */
    private final boolean lockCached;
    private final Map<String, RLock> lockMap = new ConcurrentHashMap<>();
    private final Map<String, RReadWriteLock> readWriteLockMap = new ConcurrentHashMap<>();

    public RedissonLockExecutor(RedissonHandler redissonHandler, boolean lockCached) {
        this.lockCached = lockCached;
        this.redissonHandler = redissonHandler;
    }

    @PreDestroy
    public void destroy() {
        try {
            redissonHandler.close();
        } catch (Exception e) {
            throw new LockException("Close Redisson failed", e);
        }
    }

    @Override
    public Lock tryLock(LockEntity lockEntity) throws LockException {
        if (StrUtil.isEmpty(lockEntity.getKey())) {
            throw new LockException("Composite key is null or empty");
        }
        if (!LockType.LOCK.equals(lockEntity.getType()) && lockEntity.isFair()) {
            throw new LockException("Fair lock of Redis isn't support for " + lockEntity.getType());
        }

        redissonHandler.validateStartedStatus();

        try {
            if (lockEntity.isAsync()) {
                return invokeLockAsync(lockEntity.getType(), lockEntity.getKey(), lockEntity.getLeaseTime(), lockEntity.getWaitTime(), lockEntity.isFair());
            } else {
                return invokeLock(lockEntity.getType(), lockEntity.getKey(), lockEntity.getLeaseTime(), lockEntity.getWaitTime(), lockEntity.isFair());
            }
        } catch (Exception e) {
            throw new LockException(e);
        }
    }

    @Override
    public void unLock(Lock lock) throws Exception {
        if (redissonHandler.isStarted() && lock != null) {
            lock.unLock();
        }
    }

    private Lock invokeLock(LockType lockType, String key, long leaseTime, long waitTime, boolean fair) throws Exception {
        RLock lock = getLock(lockType, key, fair);
        boolean acquired = lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);

        return acquired ? new RedissonLock(lock) : null;
    }

    private Lock invokeLockAsync(LockType lockType, String key, long leaseTime, long waitTime, boolean fair) throws Exception {
        RLock lock = getLock(lockType, key, fair);
        boolean acquired = lock.tryLockAsync(waitTime, leaseTime, TimeUnit.MILLISECONDS).get();

        return acquired ? new RedissonLock(lock) : null;
    }

    private RLock getLock(LockType lockType, String key, boolean fair) {
        if (lockCached) {
            return getCachedLock(lockType, key, fair);
        } else {
            return getNewLock(lockType, key, fair);
        }
    }

    private RLock getNewLock(LockType lockType, String key, boolean fair) {
        RedissonClient redisson = redissonHandler.getRedisson();
        switch (lockType) {
            case LOCK:
                if (fair) {
                    return redisson.getFairLock(key);
                } else {
                    return redisson.getLock(key);
                }
            case READ_LOCK:
                return getCachedReadWriteLock(key, fair).readLock();
            case WRITE_LOCK:
                return getCachedReadWriteLock(key, fair).writeLock();
            default:
                break;
        }

        throw new LockException("Invalid Redis lock type for " + lockType);
    }

    private RLock getCachedLock(LockType lockType, String key, boolean fair) {
        String newKey = StrUtil.join(":", lockType.name(), key, "fair[" + fair + "]");

        RLock lock = lockMap.get(newKey);
        if (lock == null) {
            RLock newLock = getNewLock(lockType, key, fair);
            lock = lockMap.put(newKey, newLock);
            if (lock == null) {
                lock = newLock;
            }
        }

        return lock;
    }

    private RReadWriteLock getCachedReadWriteLock(String key, boolean fair) {
        String newKey = key + "-" + "fair[" + fair + "]";

        RReadWriteLock readWriteLock = readWriteLockMap.get(newKey);
        if (readWriteLock == null) {
            RedissonClient redisson = redissonHandler.getRedisson();
            RReadWriteLock newReadWriteLock = redisson.getReadWriteLock(key);
            readWriteLock = readWriteLockMap.put(newKey, newReadWriteLock);
            if (readWriteLock == null) {
                readWriteLock = newReadWriteLock;
            }
        }

        return readWriteLock;
    }
}
