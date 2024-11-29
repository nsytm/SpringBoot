package com.example.controller;

import com.example.exception.CustomException;
import com.example.exception.WebResultEnum;
import com.example.vo.R;
import com.example.vo.UserDetailVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 天纵神威
 * @date 2024/11/14
 * @description 功能描述
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/getUserDetail")
    public void getUserDetail(@Valid @RequestBody UserDetailVO userDetailVO) {
        // 触发自定义业务异常处理方法
        if (!"1535".equals(userDetailVO.getUserId())) {
            throw new CustomException("用户不存在");
        }
        // 触发未知异常处理方法
        throw new NullPointerException("发生空指针异常");
    }

    @PostMapping("/getUserRole")
    public R<Map<String, String>> getUserRole(@RequestBody UserDetailVO userDetailVO) {
        if ("1535".equals(userDetailVO.getUserId())) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("userName", "天纵神威");
            dataMap.put("userRole", "admin");
            return R.ok(dataMap);
        } else {
            return R.fail(WebResultEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
