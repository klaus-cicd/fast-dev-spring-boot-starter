package com.fd.dds.config;

import com.fd.dds.DynamicDataSource;
import com.fd.dds.DynamicDataSourceManager;
import com.fd.dds.properties.MySqlDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Klaus
 */
public class DynamicDataSourceAutoConfiguration {

    public static final String DEFAULT_MYSQL_DATA_SOURCE = "mysqlDefaultDataSource";
    public static final String DYNAMIC_DATA_SOURCE = "fdDynamicDataSource";

    /**
     * 默认MySQL数据源
     *
     * @param mySqlDataSourceProperties MySQL数据源属性
     * @return {@link DataSource}
     */
    @Bean(DEFAULT_MYSQL_DATA_SOURCE)
    @ConditionalOnProperty(prefix = MySqlDataSourceProperties.PREFIX, name = "username")
    public DataSource mysqlDefaultDataSource(MySqlDataSourceProperties mySqlDataSourceProperties) {
        return DynamicDataSourceManager.buildDataSource(mySqlDataSourceProperties, null);
    }


    /**
     * 动态数据源
     *
     * @return {@link DataSource}
     */
    @Primary
    @Bean(DYNAMIC_DATA_SOURCE)
    @DependsOn({"com.klaus.fd.utils.BeanUtil", DEFAULT_MYSQL_DATA_SOURCE})
    public DynamicDataSource fdDynamicDataSource(DataSource mysqlDefaultDataSource) {
        Map<Object, Object> targetDataSourceMap = new ConcurrentHashMap<>(5);
        Assert.notNull(mysqlDefaultDataSource, "Create dynamic data source error, default data source not found");
        return new DynamicDataSource(mysqlDefaultDataSource, targetDataSourceMap);
    }
}
