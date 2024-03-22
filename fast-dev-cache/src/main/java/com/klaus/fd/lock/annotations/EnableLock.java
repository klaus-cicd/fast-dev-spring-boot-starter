package com.klaus.fd.lock.annotations;

import com.klaus.fd.lock.aop.EnableLockSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Klaus
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(EnableLockSelector.class)
@Inherited
public @interface EnableLock {
    AdviceMode mode() default AdviceMode.PROXY;
}
