package com.klaus.fd.exception;


/**
 * 抽象异常类
 *
 * @author klaus
 * @date 2024/01/04
 */
public class AbstractException extends RuntimeException {

    private Integer code;

    public AbstractException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public AbstractException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMsg());
        this.code = exceptionCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
