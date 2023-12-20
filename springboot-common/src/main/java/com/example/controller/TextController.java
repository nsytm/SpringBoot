package com.example.controller;

import com.example.query.ValidQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pgl
 * @description 描述
 * @date 2023/8/17
 */
@Slf4j
@RestController
public class TextController {
    
    @PostMapping("/test")
    public void test(@RequestBody ValidQuery query) {
        log.info("执行成功了!");
    }

}
