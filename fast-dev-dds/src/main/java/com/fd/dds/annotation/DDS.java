package com.fd.dds.annotation;

import com.fd.dds.enums.DDSMode;
import com.fd.dds.enums.DDSType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定动态数据源
 *
 * @author Klaus
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DDS {
    DDSType type() default DDSType.MYSQL;

    DDSMode mode() default DDSMode.DIRECT;
}
