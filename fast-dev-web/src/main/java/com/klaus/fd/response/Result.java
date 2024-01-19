package com.klaus.fd.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Klaus
 */
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Result<T> extends BaseResult<T> {
    private static final long serialVersionUID = 1L;

    /**
     * Response data
     */
    private T data;

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(Integer code, String message, String traceId) {
        super(code, message, traceId);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(data);
    }
}
