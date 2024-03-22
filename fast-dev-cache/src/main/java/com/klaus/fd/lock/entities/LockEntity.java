package com.klaus.fd.lock.entities;


import com.klaus.fd.lock.enums.LockType;

/**
 * @author Klaus
 */
public class LockEntity {
    private LockType type;
    private String key;
    private long leaseTime = 5000L;
    private long waitTime = 60000L;
    private boolean async;
    private boolean fair;


    public LockType getType() {
        return type;
    }

    public void setType(LockType type) {
        this.type = type;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getLeaseTime() {
        return leaseTime;
    }

    public void setLeaseTime(long leaseTime) {
        this.leaseTime = leaseTime;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isFair() {
        return fair;
    }

    public void setFair(boolean fair) {
        this.fair = fair;
    }

    @Override
    public String toString() {
        return String.format("%s{type=%s, key=%s, leaseTime=%s, waitTime=%s, async=%s, fair=%s}", this.getClass().getSimpleName(),
                this.type, this.key, this.leaseTime, this.waitTime, this.async, this.fair);
    }
}
