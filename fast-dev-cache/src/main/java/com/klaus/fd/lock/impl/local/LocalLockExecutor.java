package com.klaus.fd.lock.impl.local;


import cn.hutool.core.util.StrUtil;
import com.klaus.fd.lock.Lock;
import com.klaus.fd.lock.LockExecutor;
import com.klaus.fd.lock.entities.LockEntity;
import com.klaus.fd.lock.enums.LockType;
import com.klaus.fd.lock.expections.LockException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Klaus
 */
public class LocalLockExecutor implements LockExecutor {

    private final boolean lockCached;

    /**
     * 可重入锁可重复使用
     */
    private final Map<String, java.util.concurrent.locks.Lock> lockMap = new ConcurrentHashMap<>();
    private final Map<String, ReadWriteLock> readWriteLockMap = new ConcurrentHashMap<>();

    public LocalLockExecutor(boolean lockCached) {
        this.lockCached = lockCached;
    }


    @Override
    public Lock tryLock(LockEntity lockEntity) throws LockException {
        if (StrUtil.isEmpty(lockEntity.getKey())) {
            throw new LockException("Composite key is null or empty");
        }

        if (lockEntity.isAsync()) {
            throw new LockException("Async lock of Local isn't support for " + lockEntity.getType());
        }

        try {
            java.util.concurrent.locks.Lock lock = getLock(lockEntity.getType(), lockEntity.getKey(), lockEntity.isFair());
            boolean acquired = lock.tryLock(lockEntity.getWaitTime(), TimeUnit.MILLISECONDS);
            return acquired ? new LocalLock(lock) : null;
        } catch (Throwable e) {
            throw new LockException("call local lock has excetion", e);
        }

    }

    @Override
    public void unLock(Lock lock) throws Exception {
        if (lock == null) {
            return;
        }
        lock.unLock();
    }

    private java.util.concurrent.locks.Lock getLock(LockType lockType, String key, boolean fair) {
        if (lockCached) {
            return getCachedLock(lockType, key, fair);
        } else {
            return getNewLock(lockType, key, fair);
        }
    }

    private java.util.concurrent.locks.Lock getNewLock(LockType lockType, String key, boolean fair) {
        switch (lockType) {
            case LOCK:
                return new ReentrantLock(fair);
            case READ_LOCK:
                return getCachedReadWriteLock(key, fair).readLock();
            case WRITE_LOCK:
                return getCachedReadWriteLock(key, fair).writeLock();
            default:
                break;
        }

        throw new LockException("Invalid Local lock type for " + lockType);
    }

    private java.util.concurrent.locks.Lock getCachedLock(LockType lockType, String key, boolean fair) {
        String newKey = lockType + "-" + key + "-" + "fair[" + fair + "]";

        java.util.concurrent.locks.Lock lock = lockMap.get(newKey);
        if (lock == null) {
            java.util.concurrent.locks.Lock newLock = getNewLock(lockType, key, fair);
            lock = lockMap.put(newKey, newLock);
            if (lock == null) {
                lock = newLock;
            }
        }

        return lock;
    }

    private ReadWriteLock getCachedReadWriteLock(String key, boolean fair) {
        String newKey = key + "-" + "fair[" + fair + "]";

        ReadWriteLock readWriteLock = readWriteLockMap.get(newKey);
        if (readWriteLock == null) {
            ReadWriteLock newReadWriteLock = new ReentrantReadWriteLock(fair);
            readWriteLock = readWriteLockMap.put(newKey, newReadWriteLock);
            if (readWriteLock == null) {
                readWriteLock = newReadWriteLock;
            }
        }

        return readWriteLock;
    }
}
