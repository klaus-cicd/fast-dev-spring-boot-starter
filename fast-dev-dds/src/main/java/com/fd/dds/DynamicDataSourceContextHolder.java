package com.fd.dds;

import com.fd.dds.enums.DDSType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedInheritableThreadLocal;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源管理上下文对象
 *
 * @author Klaus
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    public static final String DEFAULT_DB = "default";

    /**
     * 用于管理当前已知的所有数据源
     */
    @Getter
    @Setter
    private static Map<Object, Object> allDataSource = new ConcurrentHashMap<>();
    private static final ThreadLocal<String> CONTEXT_HOLDER = new NamedInheritableThreadLocal<>(DDSType.MYSQL.getDdsKeyPrefix());

    /**
     * 切换数据源Key
     *
     * @param dataSourceKey 数据源Map对应的key, 目前采用DB Name作为key
     */
    public static void switchTo(String dataSourceKey) {
        if (log.isDebugEnabled()) {
            log.debug("DynamicDataSourceContextHolder#Switch datasource to: {} ", dataSourceKey);
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
            log.debug("DynamicDataSourceContextHolder#Remove datasource in thread local");
        }
    }

    /**
     * 清除所有数据源映射
     */
    public static void clearAllDataSourceMap() {
        allDataSource.clear();
    }

    /**
     * 添加数据源
     *
     * @param key        关键
     * @param dataSource 数据源
     */
    public static void addDataSource(String key, DataSource dataSource) {
        allDataSource.put(key, dataSource);
    }

    /**
     * 切换到默认的MySQL数据源
     */
    public static void switchToDefaultMySql() {
        switchTo(DDSType.MYSQL.getDdsKeyPrefix() + DEFAULT_DB);
    }
}
