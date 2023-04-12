package com.pgl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author pgl
 * @ClassName AsyncConfig
 * @Description
 * @Date:2023/4/12
 */
@Configuration
public class AsyncConfig {

    @Bean
    public ExecutorService myExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        return executor;
    }

}
