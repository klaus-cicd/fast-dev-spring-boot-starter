package com.klaus.fd.lock.enums;

/**
 * @author Klaus
 */
public enum LockProvider {
    LOCAL,
    REDIS;

    public static LockProvider match(String lockProviderStr) {
        for (LockProvider lockProvider : values()) {
            if (lockProvider.name().equalsIgnoreCase(lockProviderStr)) {
                return lockProvider;
            }
        }

        return null;
    }
}
