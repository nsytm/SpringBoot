package com.example.controller;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;

/**
 * @author 天纵神威
 * @date 2024/11/14
 * @description 功能描述
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/get")
    public void getUser(@RequestParam String id) throws NameNotFoundException {
        if (id == null) {
            throw new NameNotFoundException();
        }
        throw new HttpMessageNotReadableException("No such id: " + id);
    }

}
