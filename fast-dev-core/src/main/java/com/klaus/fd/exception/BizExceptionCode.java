package com.klaus.fd.exception;

/**
 * @author Silas
 */
public enum BizExceptionCode implements ExceptionCode {


    SYSTEM_ERROR(500000, "系统错误");


    private final Integer code;
    private final String msg;

    BizExceptionCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }


}
