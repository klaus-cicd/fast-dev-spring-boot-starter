package com.klaus.fd.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Klaus
 */
public class ClassUtil {

    private ClassUtil() {
    }

    /**
     * 获取class对象以及其父类的所有字段
     *
     * @param clazz 待获取的class对象
     * @return List<Field>
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>(Arrays.asList(cn.hutool.core.util.ClassUtil.getDeclaredFields(clazz)));
        Class<?> superClass = clazz.getSuperclass();
        // 递归获取父类Class的所有Field
        if (superClass != null && superClass != Object.class) {
            fieldList.addAll(getAllFields(superClass));
        }

        return fieldList;
    }

    /**
     * 获取目标类及其父类的所有Field, 并按照指定过滤条件过滤
     *
     * @param clazz  目标类Class
     * @param filter 过滤器
     * @return List<Field>
     */
    public static List<Field> getAllFields(Class<?> clazz, Predicate<Field> filter) {
        return getAllFields(clazz).stream().filter(filter).collect(Collectors.toList());
    }
}
