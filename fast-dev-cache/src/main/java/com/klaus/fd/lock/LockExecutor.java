package com.klaus.fd.lock;


import com.klaus.fd.lock.entities.LockEntity;
import com.klaus.fd.lock.expections.LockException;

/**
 * @author Klaus
 */
public interface LockExecutor {

    /**
     * 尝试获取锁，如果获取到锁，则返回锁对象，如果未获取到锁，则返回空
     *
     * @param lockEntity 锁对象
     * @return Lock
     * @throws LockException 异常
     */
    Lock tryLock(LockEntity lockEntity) throws LockException;

    /**
     * 进行解锁
     *
     * @param lock
     * @throws Exception
     */
    void unLock(Lock lock) throws Exception;
}
