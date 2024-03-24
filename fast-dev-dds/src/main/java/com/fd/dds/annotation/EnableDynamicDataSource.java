package com.fd.dds.annotation;

import com.fd.dds.DynamicDataSourceManager;
import com.fd.dds.config.DynamicDataSourceAutoConfiguration;
import com.fd.dds.properties.MySqlDataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Klaus
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({DynamicDataSourceAutoConfiguration.class, DynamicDataSourceManager.class})
@EnableConfigurationProperties(MySqlDataSourceProperties.class)
public @interface EnableDynamicDataSource {
}
