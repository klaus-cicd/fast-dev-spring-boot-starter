package com.fd.web.exception;

import cn.hutool.core.util.StrUtil;
import com.fd.web.response.Result;
import com.klaus.fd.ThreadInfoContextHolder;
import com.klaus.fd.exception.AbstractException;
import com.klaus.fd.exception.BizExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Klaus
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 表单提交错误异常捕获
     *
     * @param e BindException
     * @return {@link Result}<{@link Void}>
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result<Void> bindExceptionHandler(BindException e) {
        String msg = buildBindExceptionMsg(e.getBindingResult());
        log.error("[BindException] uri:{}, msg:{}", ThreadInfoContextHolder.getUri(), msg, e);
        return Result.badReq(msg, ThreadInfoContextHolder.getTraceId());
    }

    /**
     * 参数类型错误异常捕获
     *
     * @param e MethodArgumentTypeMismatchException
     * @return {@link Result}<{@link Void}>
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> methodArgumentTypeMismatchExceptionHandleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String message = StrUtil.format("{} should be a valid {} and {} isn't",
                e.getName(), Objects.requireNonNull(e.getRequiredType()).getSimpleName(), e.getValue(), e);
        log.error("[MethodArgumentTypeMismatchException] uri:{}, msg:{}", ThreadInfoContextHolder.getUri(), message, e);
        return Result.badReq(message, ThreadInfoContextHolder.getTraceId());
    }


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = buildBindExceptionMsg(e.getBindingResult());
        log.error("[MethodArgumentNotValidException] uri:{} msg:{}", ThreadInfoContextHolder.getUri(), msg, e);
        return Result.badReq(msg, ThreadInfoContextHolder.getTraceId());
    }

    private String buildBindExceptionMsg(BindingResult e) {
        return e.getFieldErrors()
                .stream()
                .map(n -> String.format("%s: %s", n.getField(), n.getDefaultMessage()))
                .reduce((x, y) -> String.format("%s; %s", x, y))
                .orElse("Parameter exception");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<Void> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String msg = e.getConstraintViolations()
                .stream()
                .map(constraintViolation -> {
                    String path = constraintViolation.getPropertyPath().toString();
                    String fieldName = path.substring(path.indexOf(".", -1) + 1);
                    return StrUtil.format("参数{}{}", fieldName, constraintViolation.getMessage());
                })
                .collect(Collectors.joining("; "));
        log.error("[ConstraintViolationException] uri:{}, msg:{}", ThreadInfoContextHolder.getUri(), msg, e);
        return Result.badReq(StrUtil.format("Parameter validate exception：{}", msg), ThreadInfoContextHolder.getTraceId());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result<Void> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        String msg = StrUtil.format("Param is missing: {}", e.getParameterName());
        log.error("[MissingServletRequestParameterException] uri:{}, msg:{}", ThreadInfoContextHolder.getUri(), msg, e);
        return Result.fail(msg, ThreadInfoContextHolder.getTraceId());
    }


    /**
     * AbstractException自定义异常处理
     *
     * @param exception 异常
     * @return {@link Result}<{@link Void}>
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AbstractException.class)
    public Result<Void> abstractException(AbstractException exception) {
        log.error("[AbstractException] uri:{}, msg:{}", ThreadInfoContextHolder.getUri(), exception.getMessage(), exception);
        return Result.fail(exception.getCode(), exception.getMessage(), ThreadInfoContextHolder.getTraceId());
    }

    /**
     * 兜底异常处理
     *
     * @param e Exception
     * @return R
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public Result<Void> handler(Exception e) {
        log.error("[Exception] uri:{}", ThreadInfoContextHolder.getUri(), e);
        return Result.fail(BizExceptionCode.SYSTEM_ERROR.getCode(), BizExceptionCode.SYSTEM_ERROR.getMsg(), ThreadInfoContextHolder.getTraceId());
    }

}
