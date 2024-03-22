package com.fd.dds.enums;

import lombok.Getter;

/**
 * 数据源类型
 *
 * @author Klaus
 */
@Getter
public enum DDSType {

    /**
     * mysql
     */
    MYSQL("_mysql_"),
    /**
     * mongodb
     */
    MONGODB("_mysql_"),
    /**
     * tdengine
     */
    TDENGINE("_tdengine_");

    private final String ddsKeyPrefix;

    DDSType(String ddsKeyPrefix) {
        this.ddsKeyPrefix = ddsKeyPrefix;
    }
}
