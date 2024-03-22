package com.klaus.fd.lock.impl;


import com.klaus.fd.lock.Lock;
import com.klaus.fd.lock.LockDelegate;
import com.klaus.fd.lock.LockExecutor;
import com.klaus.fd.lock.entities.LockEntity;
import com.klaus.fd.lock.entities.LockSettings;
import com.klaus.fd.lock.expections.LockException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Klaus
 */
public class LockDelegateImpl implements LockDelegate {

    final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final LockExecutor lockExecutor;
    private final LockSettings lockSettings;

    public LockDelegateImpl(LockSettings lockSettings, LockExecutor lockExecutor) {
        this.lockSettings = lockSettings;
        this.lockExecutor = lockExecutor;
    }

    @Override
    public Object invoke(ProceedingJoinPoint proceed, LockEntity lockEntity) throws Throwable {
        Lock lock = null;
        logger.debug("redissonLockDelegate start lock");
        try {
            lock = lockExecutor.tryLock(lockEntity);
            if (lock != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("redissonLockDelegate lock success, lock info {}", lockEntity.toString());
                }
                return proceed.proceed();
            }
            if (logger.isInfoEnabled()) {
                logger.info("lock failed, lock info {}", lockEntity.toString());
            }
            if (lockSettings.isThrowExceptionOnLockFailed()) {
                throw new LockException("lock failed");
            }
            return null;
        } finally {
            if (lock != null) {
                lockExecutor.unLock(lock);
                if (logger.isInfoEnabled()) {
                    logger.info("lock unlock success {}", lockEntity.toString());
                }
            }
        }
    }
}
