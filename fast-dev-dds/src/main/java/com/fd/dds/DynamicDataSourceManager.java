package com.fd.dds;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.fd.dds.enums.DDSType;
import com.fd.dds.properties.BaseDynamicDataSourceProperties;
import com.klaus.fd.constant.URLConstant;
import com.klaus.fd.utils.BeanUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源管理器
 *
 * @author Klaus
 */
@Slf4j
public class DynamicDataSourceManager implements DisposableBean {

    public static final String BEAN_NAME_DYNAMIC_DATA_SOURCE = "fdDynamicDataSource";
    public static final String DEFAULT_DB_NAME = "default";
    public static final String DEFAULT_MYSQL_DB_KEY = DDSType.MYSQL.getDdsKeyPrefix() + DEFAULT_DB_NAME;
    private static final DynamicDataSource DYNAMIC_DATA_SOURCE = BeanUtil.getBean(BEAN_NAME_DYNAMIC_DATA_SOURCE);

    /**
     * 用于管理当前已知的所有数据源
     */
    @Getter
    protected static Map<Object, Object> allDataSource = new ConcurrentHashMap<>();
    /**
     * 当前线程数据源Key, 通过切换该线程变量达到切换数据的目的
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(DDSType.MYSQL.getDdsKeyPrefix());


    /**
     * 切换数据源Key
     *
     * @param dataSourceKey 数据源Map对应的key, 目前采用DB Name作为key
     */
    public static void switchTo(String dataSourceKey) {
        if (log.isDebugEnabled()) {
            log.debug("DynamicDataSourceManager#Switch datasource to: {} ", dataSourceKey);
        }
        CONTEXT_HOLDER.set(dataSourceKey);
    }

    /**
     * 获取当前数据源变量Key
     *
     * @return 数据源Key
     */
    public static String getCurrentDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清空当前数据源Key
     */
    public static synchronized void clearCurrentDataSourceKey() {
        CONTEXT_HOLDER.remove();
        if (log.isDebugEnabled()) {
            log.debug("DynamicDataSourceManager#Remove datasource in thread local");
        }
    }


    /**
     * 添加数据源
     *
     * @param key        Key
     * @param dataSource 数据源
     */
    public static void addDataSource(String key, DataSource dataSource) {
        if (!checkConnection(key, dataSource)) {
            return;
        }
        Assert.notNull(DYNAMIC_DATA_SOURCE, "添加数据源失败, 缺少动态数据源对象");
        DYNAMIC_DATA_SOURCE.setTargetDataSources(DynamicDataSourceManager.getAllDataSource());
        allDataSource.put(key, dataSource);
    }


    /**
     * 切换到默认的MySQL数据源
     */
    public static void switchToDefaultMySql() {
        switchTo(DEFAULT_MYSQL_DB_KEY);
    }


    public static DataSource buildDataSource(BaseDynamicDataSourceProperties dataSourceProperties, String dbName) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());
        druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        druidDataSource.setUrl(dataSourceProperties.getUrlPrefix() + URLConstant.DIAGONAL + (StrUtil.isBlankIfStr(dbName) ? dataSourceProperties.getDefaultDbName() : dbName));
        if (dataSourceProperties.getDriverClassName().contains("taosdata")) {
            // TDengine
            druidDataSource.setValidationQuery("select server_status()");
        } else {
            druidDataSource.setValidationQuery("select 1");
        }
        druidDataSource.setConnectionErrorRetryAttempts(3);
        druidDataSource.setBreakAfterAcquireFailure(true);
        druidDataSource.setTimeBetweenConnectErrorMillis(1000);
        druidDataSource.setMaxWait(5000);
        return druidDataSource;
    }

    private static boolean checkConnection(String key, DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection == null) {
                log.error("DynamicDataSource failed to get connection, key:{}", key);
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("DynamicDataSource failed to get connection, key:{}", key, e);
            return false;
        }
    }

    @Override
    public void destroy() throws Exception {
        clearCurrentDataSourceKey();
        allDataSource.clear();
    }
}
