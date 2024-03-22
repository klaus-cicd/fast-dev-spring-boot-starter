package com.klaus.fd.lock.impl.redisson;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.klaus.fd.lock.RedissonHandler;
import com.klaus.fd.lock.entities.LockSettings;
import com.klaus.fd.lock.expections.LockException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Klaus
 */
public class RedissonHandlerImpl implements RedissonHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RedissonHandlerImpl.class);

    private RedissonClient redisson;

    /**
     * 创建默认Redisson
     *
     * @param lockSettings 锁配置类
     */
    public RedissonHandlerImpl(LockSettings lockSettings) {
        Assert.notBlank(lockSettings.getServer(), "lock server should no be empty!");
        Assert.isTrue(lockSettings.getPort() > 0, "lock server port should no be grater than zero!");
        try {
            Config config = new Config();
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress("redis://" + lockSettings.getServer() + ":" + lockSettings.getPort());
            // 如果无密码却设密码为空, 会使用空的密码进行验证, 然后抛出密码异常的错误
            if (StrUtil.isNotBlank(lockSettings.getPassword())) {
                singleServerConfig.setPassword(lockSettings.getPassword());
            }
            // ping的时间设置,是为了解决长时间没有请求时，会被服务端设置的timeout时间后kill
            singleServerConfig.setPingConnectionInterval(30000);
            // key 都按字符器来解析
            config.setCodec(new StringCodec());
            create(config);
        } catch (Exception e) {
            LOG.error("Initialize Redisson failed", e);
            throw new LockException("Initialize Redisson failed", e);
        }
    }


    /**
     * 使用config创建Redisson
     *
     * @param config Redisson 配置对象
     * @throws LockException 锁异常
     */
    private void create(Config config) throws LockException {
        LOG.info("Start to initialize Redisson...");

        if (redisson != null) {
            throw new LockException("Redisson isn't null, it has been initialized already");
        }

        redisson = Redisson.create(config);
    }

    /**
     * 关闭Redisson客户端连接
     *
     * @throws LockException 锁异常
     */
    @Override
    public void close() throws LockException {
        LOG.info("Start to close Redisson...");

        validateStartedStatus();

        redisson.shutdown();
    }

    /**
     * 获取Redisson客户端是否初始化
     *
     * @return
     */
    @Override
    public boolean isInitialized() {
        return redisson != null;
    }

    /**
     * 获取Redisson客户端连接是否正常
     *
     * @return 锁是否开启
     */
    @Override
    public boolean isStarted() {
        if (redisson == null) {
            throw new LockException("Redisson is null");
        }

        return !redisson.isShutdown() && !redisson.isShuttingDown();
    }

    /**
     * 检查Redisson是否是启动状态
     *
     * @throws LockException
     */
    @Override
    public void validateStartedStatus() throws LockException {
        if (redisson == null) {
            throw new LockException("Redisson is null");
        }

        if (!isStarted()) {
            throw new LockException("Redisson is closed");
        }
    }

    /**
     * 检查Redisson是否是关闭状态
     *
     * @throws Exception
     */
    @Override
    public void validateClosedStatus() throws Exception {
        if (redisson == null) {
            throw new LockException("Redisson is null");
        }

        if (isStarted()) {
            throw new LockException("Redisson is started");
        }
    }

    /**
     * 获取Redisson客户端
     *
     * @return
     */
    @Override
    public RedissonClient getRedisson() {
        return redisson;
    }
}
