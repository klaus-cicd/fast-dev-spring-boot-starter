package com.fd.dds.exception;

import com.klaus.fd.exception.ExceptionCode;
import lombok.Getter;

/**
 * 动态数据源异常
 *
 * @author Klaus
 * @date 2024/03/16
 */
@Getter
public enum DynamicDataSouceExceptionCode implements ExceptionCode {
    /**
     * 添加数据源失败
     */
    ADD_FAIL(60001, "Data source add fail!");

    private final Integer code;
    private final String message;

    DynamicDataSouceExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
