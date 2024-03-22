package com.klaus.fd.exception;

/**
 * @author Klaus
 */
public class JsonException extends AbstractException {

    public JsonException(Integer code, String message) {
        super(code, message);
    }

    public JsonException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
