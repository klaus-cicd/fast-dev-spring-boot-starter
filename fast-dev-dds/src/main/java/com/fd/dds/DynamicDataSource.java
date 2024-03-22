package com.fd.dds;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.fd.dds.enums.DDSType;
import com.fd.dds.exception.DynamicDataSouceExceptionCode;
import com.fd.dds.exception.DynamicDataSourceException;
import com.fd.dds.properties.BaseDynamicDataSourceProperties;
import com.klaus.fd.constant.URLConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author Klaus
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {


    public DynamicDataSource(Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(targetDataSources.get(DDSType.MYSQL.getDdsKeyPrefix() + DynamicDataSourceContextHolder.DEFAULT_DB));
        this.setTargetDataSources(targetDataSources);
        DynamicDataSourceContextHolder.setAllDataSource(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String datasourceType = DynamicDataSourceContextHolder.getCurrentDataSourceKey();
        if (log.isDebugEnabled()) {
            log.debug("DynamicDataSource switch to data source: {}", datasourceType);
        }
        return datasourceType;
    }


    @Override
    public void setTargetDataSources(@NonNull Map<Object, Object> targetDataSources) {
        DynamicDataSourceContextHolder.setAllDataSource(targetDataSources);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    public DataSource addDataSource(
            String key, BaseDynamicDataSourceProperties dataSourceProperties, String dbName) {
        DataSource dataSource = buildDataSource(dataSourceProperties, dbName);
        if (!checkConnection(dataSource, dbName)) {
            throw new DynamicDataSourceException(DynamicDataSouceExceptionCode.ADD_FAIL);
        }

        DynamicDataSourceContextHolder.addDataSource(key, dataSource);
        DynamicDataSourceContextHolder.getAllDataSource().put(dbName, dataSource);
        super.setTargetDataSources(DynamicDataSourceContextHolder.getAllDataSource());
        super.afterPropertiesSet();
        log.info("Add data source, database name: {}", dbName);
        return dataSource;
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

    private static boolean checkConnection(DataSource dataSource, String bdName) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection == null) {
                log.error("DynamicDataSource failed to get connection from db: {}", bdName);
                return false;
            }
        } catch (Exception e) {
            log.error("DynamicDataSource failed to get connection from db: {}", bdName, e);
            return false;
        }
        return true;
    }
}
