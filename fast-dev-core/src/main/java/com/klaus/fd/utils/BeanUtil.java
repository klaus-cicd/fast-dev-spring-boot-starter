package com.klaus.fd.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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

    public <T> T getBeanByType(Class<T> targetClass) {
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
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtil.applicationContext = applicationContext;
    }
}
