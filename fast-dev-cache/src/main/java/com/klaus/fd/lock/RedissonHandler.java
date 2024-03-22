package com.klaus.fd.lock;

import com.klaus.fd.lock.expections.LockException;
import org.redisson.api.RedissonClient;

/**
 * @author Klaus
 */
public interface RedissonHandler {
    /**
     * 关闭Redisson客户端连接
     *
     * @throws Exception
     */
    void close() throws Exception;

    /**
     * 获取Redisson客户端是否初始化
     *
     * @return
     */
    boolean isInitialized();

    /**
     * 获取Redisson客户端连接是否正常
     *
     * @return
     */
    boolean isStarted();

    /**
     * 检查Redisson是否是启动状态
     *
     * @throws LockException
     */
    void validateStartedStatus() throws LockException;

    /**
     * 检查Redisson是否是关闭状态
     *
     * @throws Exception
     */
    void validateClosedStatus() throws Exception;

    /**
     * 获取Redisson客户端
     *
     * @return
     */
    RedissonClient getRedisson();
}
