package com.fd.dds.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MySQL数据源属性对象
 *
 * @author Klaus
 * @date 2024/03/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(MySqlDataSourceProperties.PREFIX)
public class MySqlDataSourceProperties implements BaseDynamicDataSourceProperties {

    public static final String PREFIX = "spring.datasource.dynamic.mysql";

    private String username;
    private String password;
    private String urlPrefix;
    private String driverClassName;
    private String defaultDbName;
}
