package com.hzy.controller;

import com.hzy.domain.ResponseResult;
import com.hzy.domain.User;
import com.hzy.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: hzy
 * Date: 2023/5/15
 * Time: 23:31
 * Description:
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {

        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout() {

        return loginService.logout();
    }
}
