package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 天纵神威
 * @date 2024/12/13
 * @description 功能描述
 */
@Slf4j
@RestController
@RequestMapping("/api/log")
public class LogbackController {

    @PostMapping("/demo")
    public void demo() {
        log.info("logback demo!");
    }

}
