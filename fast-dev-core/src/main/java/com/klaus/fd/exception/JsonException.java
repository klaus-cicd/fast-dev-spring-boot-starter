package com.klaus.fd.exception;

/**
 * @author Silas
 */
public class JsonException extends AbstractException {

    public JsonException(String message, Integer code) {
        super(message, code);
    }

    public JsonException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
