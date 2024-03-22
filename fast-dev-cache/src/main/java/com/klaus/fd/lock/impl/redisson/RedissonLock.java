package com.klaus.fd.lock.impl.redisson;


import com.klaus.fd.lock.Lock;
import org.redisson.api.RLock;

/**
 * @author Klaus
 */
public class RedissonLock implements Lock {

    private final RLock lock;

    public RedissonLock(RLock lock) {
        this.lock = lock;
    }

    @Override
    public void unLock() {
        if (this.lock != null && this.lock.isHeldByCurrentThread() && this.lock.isLocked()) {
            this.lock.unlock();
        }
    }
}
