package com.fd.dds.enums;

/**
 * 动态数据源模式
 *
 * @author Klaus
 * @date 2024/03/13
 */
public enum DDSMode {

    /**
     * 直接模式
     * 需要编码时就确定数据源
     */
    DIRECT,

    /**
     * 方法入参模式
     * 根据方法的
     */
    METHOD_PARAM;
}
