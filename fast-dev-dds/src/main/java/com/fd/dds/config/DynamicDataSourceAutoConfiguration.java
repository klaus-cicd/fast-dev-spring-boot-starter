package com.fd.dds.config;

import com.fd.dds.DynamicDataSource;
import com.fd.dds.DynamicDataSourceManager;
import com.fd.dds.properties.MySqlDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Klaus
 */
public class DynamicDataSourceAutoConfiguration {


    /**
     * 默认MySQL数据源
     *
     * @param mySqlDataSourceProperties MySQL数据源属性
     * @return {@link DataSource}
     */
    @Bean
    @ConditionalOnProperty(prefix = MySqlDataSourceProperties.PREFIX, name = "username")
    public DataSource mysqlDefaultDataSource(MySqlDataSourceProperties mySqlDataSourceProperties) {
        return DynamicDataSourceManager.buildDataSource(mySqlDataSourceProperties, null);
    }


    /**
     * 动态数据源
     *
     * @return {@link DataSource}
     */
    @Bean
    @Primary
    public DynamicDataSource fdDynamicDataSource(DataSource mysqlDefaultDataSource) {
        Map<Object, Object> targetDataSourceMap = new ConcurrentHashMap<>(5);
        Assert.notNull(mysqlDefaultDataSource, "Create dynamic data source error, default data source not found");
        return new DynamicDataSource(mysqlDefaultDataSource, targetDataSourceMap);
    }
}
