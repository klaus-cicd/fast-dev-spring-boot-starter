package com.klaus.fd.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author Klaus
 */
@Slf4j
public class LambdaUtil {

    public static <T> String getFiledNameByGetter(Function<T, ?> getterFunc) {
        try {
            Method method = ReflectUtil.getMethodByName(getterFunc.getClass(), "writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(getterFunc);
            String methodName = serializedLambda.getImplMethodName();
            if (methodName.startsWith("get")) {
                methodName = methodName.substring(3);
            } else if (methodName.startsWith("is")) {
                methodName = methodName.substring(2);
            }
            return StrUtil.lowerFirst(methodName);
        } catch (Exception e) {
            log.error("LambdaUtil#getFiledNameByGetterMethod error:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
