package com.example.global;

import com.example.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author 天纵神威
 * @date 2024/11/14
 * @description 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理Http请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R<String> handle(HttpRequestMethodNotSupportedException e) {
        log.error("Http请求方法不支持异常：", e);
        return R.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), "请求方法不支持");
    }

    /**
     * 处理Http消息不可读异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<String> handle(HttpMessageNotReadableException e) {
        log.error("Http消息不可读异常：", e);
        return R.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 处理方法参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<String> handle(MethodArgumentTypeMismatchException e) {
        log.error("方法参数类型不匹配异常：", e);
        return R.fail(HttpStatus.BAD_REQUEST.value(), "方法参数类型不匹配");
    }


    // TODO 自行添加其他异常


    /**
     * 兜底异常，用于兜底处理未预期的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handle(Exception e) {
        log.error("服务器内部错误：", e);
        return R.fail("服务器内部错误，请联系管理员");
    }

}
