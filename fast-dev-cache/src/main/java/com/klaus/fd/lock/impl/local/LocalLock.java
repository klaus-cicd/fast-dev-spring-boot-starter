package com.klaus.fd.lock.impl.local;


import com.klaus.fd.lock.Lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Klaus
 */
public class LocalLock implements Lock {

    private final java.util.concurrent.locks.Lock lock;

    public LocalLock(java.util.concurrent.locks.Lock lock) {
        this.lock = lock;
    }

    @Override
    public void unLock() {
        if (lock == null) {
            return;
        }
        if (lock instanceof ReentrantLock) {
            ReentrantLock reentrantLock = (ReentrantLock) lock;
            // 只有ReentrantLock提供isLocked方法
            if (reentrantLock.isLocked()) {
                reentrantLock.unlock();
            }
        } else {
            lock.unlock();
        }
    }
}
