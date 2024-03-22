package com.fd.dds.properties;

/**
 * 基本数据源属性
 *
 * @author klaus
 * @date 2024/03/21
 */
public interface BaseDynamicDataSourceProperties {

    /**
     * 获得用户名
     *
     * @return {@link String}
     */
    String getUsername();

    /**
     * 得到密码
     *
     * @return {@link String}
     */
    String getPassword();

    /**
     * url前缀
     *
     * @return {@link String}
     */
    String getUrlPrefix();

    /**
     * 驱动类名
     *
     * @return {@link String}
     */
    String getDriverClassName();

    /**
     * 默认db名称
     *
     * @return {@link String}
     */
    String getDefaultDbName();
}