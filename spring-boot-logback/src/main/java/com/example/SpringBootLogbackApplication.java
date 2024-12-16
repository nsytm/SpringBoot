package com.example;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBootLogbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLogbackApplication.class, args);

        // SpringBoot 默认的日志框架是什么呢？
        log.info("SpringBoot 默认日志框架：{}", LoggerFactory.getILoggerFactory().getClass());

        // 常用日志级别，优先级从高到低分别是ERROR、WARN、INFO、DEBUG。
        log.error("error 日志输出");
        log.warn("warn 日志输出");
        log.info("info 日志输出");
        log.debug("debug 日志输出");

        // 如果将log level设置在某一个级别上，那么比此级别优先级高的log都能打印出来。

    }

}
