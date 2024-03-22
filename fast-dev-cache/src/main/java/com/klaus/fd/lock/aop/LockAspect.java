package com.klaus.fd.lock.aop;


import com.klaus.fd.lock.LockDelegate;
import com.klaus.fd.lock.annotations.Lock;
import com.klaus.fd.lock.constants.LockConstants;
import com.klaus.fd.lock.entities.LockEntity;
import com.klaus.fd.lock.entities.LockSettings;
import com.klaus.fd.lock.expections.LockException;
import com.klaus.fd.lock.provider.LockMethodProvider;
import com.klaus.fd.lock.utils.KeyUtils;
import com.klaus.fd.utils.SpelUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 按调用者配置的 {@link Lock} 进行锁定逻辑
 * 异常分成两种，当锁定的过程中抛出异常，会抛出{@link LockException} 异常，非{@link LockException}
 * 在{@link LockSettings} 可以进行配置 {@literal throwExceptionOnLockFailed},默认为true，在抛出 {@link LockException} 处理成异常，否则锁定失败也不
 * 抛错，返回{@literal null}
 * 同时可以设置 {@link Lock}中的属性 {@literal callFallbackMethodOnlyLockException}，去设置回调方法{@literal fallbackMethod}的执行时机，是否只有锁定阶段才会调用，默认为true
 * 否则，在锁定的AOP阶段，有异常，都会调用 {@literal fallbackMethod}
 */
@Aspect
public class LockAspect {

    final Logger logger = LoggerFactory.getLogger(LockAspect.class);
    private final LockDelegate lockDelegate;
    @Value("${" + LockConstants.PREFIX + ":lock_obj}")
    private String prefix;

    public LockAspect(LockDelegate lockDelegate) {
        this.lockDelegate = lockDelegate;
    }


    @Around("@annotation(lock)")
    public Object execute(final ProceedingJoinPoint proceed, Lock lock) throws Throwable {
        String spelKey = SpelUtil.parseByMethParams(proceed, lock.key());
        String tPrefix = prefix;

        String compositeKey = KeyUtils.getCompositeKey(tPrefix, lock.name(), spelKey);

        LockEntity lockEntity = new LockEntity();
        lockEntity.setType(lock.type());
        lockEntity.setAsync(lock.async());
        lockEntity.setFair(lock.fair());
        lockEntity.setKey(compositeKey);
        lockEntity.setLeaseTime(lock.leaseTime());
        lockEntity.setWaitTime(lock.waitTime());
        if (logger.isInfoEnabled()) {
            logger.info("execute lock , lock key =[{}] ,lock type=[{}] ,lock lease time=[{}ms] ,lock wait time=[{}ms]", compositeKey, lock.type(), lock.leaseTime(), lock.waitTime());
        }
        try {
            return lockDelegate.invoke(proceed, lockEntity);
        } catch (LockException e) {
            // LockException 时，不管是否配置callFallbackMethodOnlyLockException都先判断fallbackMethod
            if (StringUtils.hasText(lock.fallbackMethod())) {
                if (logger.isInfoEnabled()) {
                    logger.info("call fallback method : " + lock.fallbackMethod());
                }
                return resumeFallback(proceed, lock.fallbackMethod());
            }
            if (logger.isErrorEnabled()) {
                logger.error("lock exception no fallback method, throw exception directly", e);
            }
            throw e;
        } catch (Throwable e) {
            if (!lock.callFallbackMethodOnlyLockException() && StringUtils.hasText(lock.fallbackMethod())) {
                return resumeFallback(proceed, lock.fallbackMethod());
            }
            if (logger.isErrorEnabled()) {
                logger.error("no fallback method, throw exception directly", e);
            }
            throw e;
        }


    }

    private Object resumeFallback(ProceedingJoinPoint proceedingJoinPoint, String fallbackMethod) throws Throwable {
        Class clz = proceedingJoinPoint.getSignature().getDeclaringType();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = LockMethodProvider.instance().getMethod(clz, fallbackMethod, methodSignature.getParameterTypes());
        if (method == null) {
            throw new RuntimeException("class " + clz.getName() + " do not have method " + fallbackMethod);
        }
        try {
            ReflectionUtils.makeAccessible(method);
            return method.invoke(proceedingJoinPoint.getTarget(), proceedingJoinPoint.getArgs());
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }


}
