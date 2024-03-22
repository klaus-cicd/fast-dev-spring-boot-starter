package com.klaus.fd.utils;

import cn.hutool.core.lang.Assert;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SPEL表达式工具类
 *
 * @author Kluas
 */
public class SpelUtil {

    public static String parseByMethParams(ProceedingJoinPoint proceed, String key) {
        Assert.notBlank(key, "SPEL key can't be blank");

        MethodSignature methodSignature = (MethodSignature) proceed.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] arguments = proceed.getArgs();
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; ++i) {
            context.setVariable(parameterNames[i], arguments[i]);
        }
        return new SpelExpressionParser().parseExpression(key).getValue(context, String.class);

    }
}
