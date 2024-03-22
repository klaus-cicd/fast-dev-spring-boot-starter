package com.klaus.fd.lock.enums;

/**
 * @author Klaus
 */
public enum LockType {
    // 普通锁
    LOCK("Lock"),

    // 读锁
    READ_LOCK("ReadLock"),

    // 写锁
    WRITE_LOCK("WriteLock");

    private String value;

    private LockType(String value) {
        this.value = value;
    }

    public static LockType fromString(String value) {
        for (LockType type : LockType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Mismatched type with value=" + value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
