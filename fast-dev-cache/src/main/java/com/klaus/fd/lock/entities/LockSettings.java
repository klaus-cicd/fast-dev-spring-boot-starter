package com.klaus.fd.lock.entities;


import com.klaus.fd.lock.enums.LockProvider;
import lombok.Builder;

/**
 * @author Klaus
 */
@Builder
public class LockSettings {

    private String server;

    private String password;

    private int port;

    /**
     * 选择锁类型
     * LOCAL: JDK并发锁
     * REDIS: Redisson分布式锁
     */
    private LockProvider provider = LockProvider.LOCAL;

    /**
     * 锁定失败时，是否要抛出异常，如果为false，则不抛出异常，并返回null
     */
    private boolean throwExceptionOnLockFailed = true;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public LockProvider getProvider() {
        return provider;
    }

    public void setProvider(LockProvider provider) {
        this.provider = provider;
    }

    public boolean isThrowExceptionOnLockFailed() {
        return throwExceptionOnLockFailed;
    }

    public void setThrowExceptionOnLockFailed(boolean throwExceptionOnLockFailed) {
        this.throwExceptionOnLockFailed = throwExceptionOnLockFailed;
    }
}
