package com.klaus.fd.exception;

/**
 * Bean异常
 *
 * @author klaus
 * @date 2024/01/04
 */
public enum BeanExceptionCode implements ExceptionCode {
    /**
     * Bean转换异常
     */
    CONVERT_ERROR(60001, "Convert bean error");

    BeanExceptionCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
