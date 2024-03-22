package com.klaus.fd.lock;

import com.klaus.fd.lock.entities.LockEntity;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Klaus
 */
public interface LockDelegate {
    Object invoke(ProceedingJoinPoint proceed, LockEntity lockEntity) throws Throwable;
}
