package com.example.all.demo.annotation.RestControllerAdvice;

import com.example.all.util.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author pgl
 * @description RequestBodyAdvice 拦截所有以 @RequestBody 接收参数的请求
 * @date 2023/8/11
 */
@Slf4j
@RestControllerAdvice
public class DoRequestBodyAdvice implements RequestBodyAdvice {

    /**
     * 该方法用于判断当前请求, 是否要执行 beforeBodyRead 和 afterBodyRead 方法
     *
     * @param methodParameter 方法的参数对象
     * @param targetType      方法的参数类型
     * @param converterType   HTTP request 和 response 的转换器
     * @return 第一次返回 true 执行 beforeBodyRead, 第二次返回 true 执行 afterBodyRead
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 定义拦截规则或者 return true 全部放行
        return true;
    }

    /**
     * 在 @RequestBody 接收参数之前执行
     *
     * @param inputMessage  客户端请求数据
     * @param parameter     方法的参数对象
     * @param targetType    方法的参数类型
     * @param converterType HTTP request 和 response 的转换器
     * @return 返回一个自定义的 HttpInputMessage
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("supports 方法第一次执行, beforeBodyRead");
        try (InputStream body = inputMessage.getBody()) {
            String requestBodyStr = StreamUtils.toString(body);
            if (StringUtils.isNotBlank(requestBodyStr)) {
                // requestMessage 处理逻辑, 例如解密

                // 构建并返回新的 HttpInputMessage
                InputStream inputStream = new ByteArrayInputStream(requestBodyStr.getBytes(StandardCharsets.UTF_8));
                return new HttpInputMessage() {
                    @Override
                    public InputStream getBody() {
                        return inputStream;
                    }

                    @Override
                    public HttpHeaders getHeaders() {
                        return inputMessage.getHeaders();
                    }
                };
            }
        } catch (Exception e) {
            log.error("处理请求报文发生异常: ", e);
            throw new RuntimeException("处理请求报文发生异常!");
        }
        return inputMessage;
    }

    /**
     * 在 @RequestBody 接收参数之后执行
     *
     * @param body          客户端请求体数据
     * @param inputMessage  客户端请求数据
     * @param parameter     方法的参数对象
     * @param targetType    方法的参数类型
     * @param converterType HTTP request 和 response 的转换器
     * @return 返回请求体数据
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("supports 方法第二次执行, afterBodyRead");
        return body;
    }

    /**
     * 请求体为空时执行
     *
     * @param body          客户端请求体数据
     * @param inputMessage  客户端请求数据
     * @param parameter     方法的参数对象
     * @param targetType    方法的参数类型
     * @param converterType HTTP request 和 response 的转换器
     * @return 返回请求体数据
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}
