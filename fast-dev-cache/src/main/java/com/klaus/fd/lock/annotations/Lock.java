package com.klaus.fd.lock.annotations;


import com.klaus.fd.lock.enums.LockType;

import java.lang.annotation.*;

/**
 * @author Klaus
 * @date 2022/7/20
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Lock {

    /**
     * 锁的类型
     *
     * @return LockType
     */
    LockType type() default LockType.LOCK;

    /**
     * 锁的名字
     *
     * @return String
     */
    String name() default "";


    /**
     * 锁的key
     *
     * @return String
     */
    String key() default "";

    /**
     * 持锁时间，持锁超过此时间则自动丢弃锁
     * 单位毫秒，默认5秒
     *
     * @return long
     */
    long leaseTime() default 5000L;

    /**
     * 没有获取到锁时，等待时间
     * 单位毫秒，默认60秒
     *
     * @return long
     */
    long waitTime() default 60000L;

    /**
     * 是否采用锁的异步执行方式(异步拿锁，同步阻塞)
     *
     * @return boolean
     */
    boolean async() default false;

    /**
     * 是否采用公平锁
     *
     * @return boolean
     */
    boolean fair() default false;

    /**
     * 指定 加锁失败时的回调方法，同时这个方法的参数要加锁的方法的参数相同
     *
     * @return
     */
    String fallbackMethod() default "";

    /**
     * 为true时 回调FallbackMethod方法，只在LockException的时候进行处理，业务抛出异常不处理
     * false ,只锁定逻辑时或是执行锁定块里的业务逻辑抛错时，也会回调fallbackMethod
     *
     * @return
     */
    boolean callFallbackMethodOnlyLockException() default true;

}
