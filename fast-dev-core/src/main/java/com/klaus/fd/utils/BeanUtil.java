package com.klaus.fd.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * Bean工具
 *
 * @author klaus
 * @date 2024/01/04
 */
@Slf4j
public class BeanUtil extends BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> targetClass) {
        return applicationContext.getBean(targetClass);
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
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("BeanUtil.applicationContext未注入");
        }
    }
}
