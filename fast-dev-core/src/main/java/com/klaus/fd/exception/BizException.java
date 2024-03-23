package com.klaus.fd.exception;

/**
 * @author Silas
 */
public class BizException extends AbstractException {
    public BizException(Integer code, String message) {
        super(code, message);
    }

    public BizException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
