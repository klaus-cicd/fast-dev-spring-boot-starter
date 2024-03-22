package com.klaus.fd.lock.expections;

/**
 * @author Klaus
 */
public class LockException extends RuntimeException {
    private static final long serialVersionUID = 4550515832057492430L;

    public LockException() {
        super();
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockException(Throwable cause) {
        super(cause);
    }
}
