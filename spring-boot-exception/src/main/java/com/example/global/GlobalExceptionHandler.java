package com.example.global;

import com.example.exception.CustomException;
import com.example.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

/**
 * @author 天纵神威
 * @date 2024/11/14
 * @description 全局异常处理器
 * 使用 @RestControllerAdvice 注解，标志这是一个全局的控制器增强类，专门用于捕获控制器层抛出的异常并处理。
 * 使用 @ExceptionHandler 注解，捕获指定异常。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 Http 请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    // @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R<String> handle(HttpRequestMethodNotSupportedException e) {
        log.error("Http请求方法不支持异常! 原因是: ", e);
        return R.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), "请求方法不支持");
    }

    /**
     * 处理方法参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<String> handle(MethodArgumentTypeMismatchException e) {
        log.error("方法参数类型不匹配异常! 原因是: ", e);
        return R.fail(HttpStatus.BAD_REQUEST.value(), "方法参数类型不匹配");
    }


    // TODO 自行添加其他异常


    /**
     * 处理参数校验失败异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handle(MethodArgumentNotValidException e) {
        log.error("参数校验异常! 原因是: ", e);
        // 收集所有校验错误的默认消息，并用逗号隔开
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.error("参数校验异常! 原因是: {}", errorMessage);
        return R.fail(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(CustomException.class)
    public R<String> handle(CustomException e) {
        log.error("业务异常! 原因是: ", e);
        return R.fail(e.getMessage());
    }

    /**
     * 处理未知的异常
     */
    @ExceptionHandler(Exception.class)
    public R<String> handle(Exception e) {
        log.error("未知异常! 原因是: ", e);
        return R.fail();
    }

}
