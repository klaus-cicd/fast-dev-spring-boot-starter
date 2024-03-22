package com.klaus.fd.exception;

/**
 * @author Klaus
 */
public enum JsonExceptionCode implements ExceptionCode {

    /**
     * 解析错误
     */
    PARSE_ENTITY_ERROR(60001, "JSON对象解析错误"),
    /**
     * 转换至JSON字符串错误
     */
    CONVERT_JSON_STR_ERROR(60002, "转换JSON字符串错误"),
    /**
     * 解析列表错误
     */
    PARSE_LIST_ERROR(60003, "JSON列表解析错误");

    private final Integer code;
    private final String msg;

    JsonExceptionCode(Integer code, String msg) {
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
