package com.fd.dds.config;

import com.fd.dds.DynamicDataSource;
import com.fd.dds.DynamicDataSourceContextHolder;
import com.fd.dds.enums.DDSType;
import com.fd.dds.properties.MySqlDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Klaus
 */
public class DynamicDataSourceConfig {

    public static final String DEFAULT_MYSQL_DATA_SOURCE = "mysqlDefaultDataSource";

    /**
     * 默认MySQL数据源
     *
     * @param mySqlDataSourceProperties MySQL数据源属性
     * @return {@link DataSource}
     */
    @Bean(DEFAULT_MYSQL_DATA_SOURCE)
    @ConditionalOnProperty(prefix = MySqlDataSourceProperties.PREFIX, name = "username")
    public DataSource mysqlDefaultDataSource(MySqlDataSourceProperties mySqlDataSourceProperties) {
        return DynamicDataSource.buildDataSource(mySqlDataSourceProperties, null);
    }


    /**
     * 动态数据源
     *
     * @return {@link DataSource}
     */
    @Bean
    @Primary
    @DependsOn({"com.klaus.fd.utils.BeanUtil", DEFAULT_MYSQL_DATA_SOURCE})
    public DataSource dynamicDataSource(DataSource mysqlDefaultDataSource) {
        Map<Object, Object> targetDataSourceMap = new ConcurrentHashMap<>(5);
        if (Objects.nonNull(mysqlDefaultDataSource)) {
            targetDataSourceMap.put(DDSType.MYSQL.getDdsKeyPrefix() + DynamicDataSourceContextHolder.DEFAULT_DB, mysqlDefaultDataSource);
        }
        return new DynamicDataSource(targetDataSourceMap);
    }
}
