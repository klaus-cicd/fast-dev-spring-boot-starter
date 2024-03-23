package com.klaus.fd.utils;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.Map;

/**
 * Bean工具
 *
 * @author klaus
 * @date 2024/01/04
 */
@Slf4j
public class BeanUtil extends BeanUtils implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> targetClass) {
        checkApplicationContext();
        try {
            return applicationContext.getBean(targetClass);
        } catch (BeansException e) {
            return null;
        }
    }


    /**
     * 获取类型的BeanMap
     * key: Bean name
     * Value: Bean
     *
     * @param clazz clazz
     * @return {@link Map}<{@link String}, {@link T}>
     */
    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        checkApplicationContext();
        try {
            return applicationContext.getBeansOfType(clazz);
        } catch (BeansException e) {
            return Collections.emptyMap();
        }
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        try {
            return (T) applicationContext.getBean(name);
        } catch (BeansException e) {
            return null;
        }
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("BeanUtil.applicationContext registration failed");
        }
    }

    /**
     * 获取Aop代理对象
     *
     * @param invoker 调用程序
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }
}
