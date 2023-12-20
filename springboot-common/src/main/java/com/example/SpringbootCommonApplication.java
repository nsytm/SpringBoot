package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pgl
 */
@Slf4j
@SpringBootApplication
public class SpringbootCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCommonApplication.class, args);
        log.info("SpringBoot is running!");
    }

}
