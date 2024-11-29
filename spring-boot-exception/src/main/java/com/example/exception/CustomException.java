package com.example.exception;

/**
 * @author 天纵神威
 * @date 2024/11/28
 * @description 自定义业务异常
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

}
