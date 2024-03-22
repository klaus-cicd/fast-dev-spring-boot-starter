package com.klaus.fd.lock.aop;

import com.klaus.fd.lock.LockConfig;
import com.klaus.fd.lock.annotations.EnableLock;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * @author Klaus
 */
public class EnableLockSelector extends AdviceModeImportSelector<EnableLock> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        if (adviceMode != AdviceMode.PROXY) {
            throw new IllegalStateException("@EnableLockonly support PROXY advice mode.");
        }
        return new String[]{LockConfig.class.getName()};
    }

}
