package com.klaus.fd.response;

import com.klaus.fd.exception.ExceptionCode;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author Klaus
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Result<T> extends BaseResult {
    private static final long serialVersionUID = 1L;

    /**
     * Response data
     */
    private T data;

    public Result(T data) {
        this.data = data;
        super.setCode(HttpStatus.OK.value());
        super.setMessage("Success");
    }

    public Result(Integer code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public Result(Integer code, String message, String traceId, T data) {
        super(code, message, traceId);
        this.data = data;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(HttpStatus.OK.value(), "Success", data);
    }

    public static Result<Void> fail() {
        return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail", null);
    }

    public static Result<Void> fail(ExceptionCode exceptionCode) {
        return new Result<>(exceptionCode.getCode(), exceptionCode.getMsg(), null, null);
    }

    public static Result<Void> fail(ExceptionCode exceptionCode, String traceId) {
        return new Result<>(exceptionCode.getCode(), exceptionCode.getMsg(), traceId, null);
    }

    public static Result<Void> fail(Integer code, String msg) {
        return new Result<>(code, msg, null, null);
    }

    public static Result<Void> fail(Integer code, String msg, String traceId) {
        return new Result<>(code, msg, traceId, null);
    }
}
