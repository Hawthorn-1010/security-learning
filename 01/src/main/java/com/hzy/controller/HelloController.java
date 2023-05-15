package com.hzy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: hzy
 * Date: 2023/5/15
 * Time: 14:23
 * Description:
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String Hello() {
        return "hello";
    }
}
