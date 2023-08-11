package com.example.all.demo.annotation.Valid;

import com.example.all.query.ValidQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pgl
 * @description 参数校验
 * @date 2023/8/11
 */
@Slf4j
@RestController
public class DoValid {

    @PostMapping("/demo/valid")
    public void doValid(@Validated @RequestBody ValidQuery validQuery) {
        log.info("执行成功了!");
    }

}
