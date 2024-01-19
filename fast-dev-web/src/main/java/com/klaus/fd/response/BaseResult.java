package com.klaus.fd.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.klaus.fd.exception.ExceptionCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Kluas
 */
@Data
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Response code
     */
    private Integer code;

    /**
     * Response message
     */
    private String message;

    /**
     * traceId
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traceId;

    public BaseResult() {
        this.code = 20000;
        this.message = "Success";
    }

    public BaseResult(Integer code, String message, String traceId) {
        this.code = code;
        this.message = message;
        this.traceId = traceId;
    }

    public static BaseResult<Void> fail() {
        return new BaseResult<>(500000, "Fail request", "100000000");
    }

    public static BaseResult<Void> fail(ExceptionCode exceptionCode, String traceId) {
        return new BaseResult<>(exceptionCode.getCode(), exceptionCode.getMsg(), traceId);
    }
}
