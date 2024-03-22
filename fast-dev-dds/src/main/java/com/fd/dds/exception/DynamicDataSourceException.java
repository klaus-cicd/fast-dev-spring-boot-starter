package com.fd.dds.exception;

import com.klaus.fd.exception.AbstractException;
import com.klaus.fd.exception.ExceptionCode;

/**
 * @author Klaus
 */
public class DynamicDataSourceException extends AbstractException {


    public DynamicDataSourceException(Integer code, String message) {
        super(code, message);
    }

    public DynamicDataSourceException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
