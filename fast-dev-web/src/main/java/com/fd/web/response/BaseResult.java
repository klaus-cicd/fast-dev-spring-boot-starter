package com.fd.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Kluas
 */
@Data
@NoArgsConstructor
public class BaseResult implements Serializable {

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

    public BaseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(Integer code, String message, String traceId) {
        this.code = code;
        this.message = message;
        this.traceId = traceId;
    }
}
