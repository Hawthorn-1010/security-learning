package com.hzy.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: hzy
 * Date: 2022/9/12
 * Time: 22:20
 * Description:
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserInfo create(@RequestBody @Validated UserInfo user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserInfo update(@RequestBody UserInfo user) {
        return userService.update(user);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserInfo get(@PathVariable Integer id, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
        if (userInfo == null || !userInfo.getId().equals(id)) {
            throw new RuntimeException("身份认证信息异常，获取用户信息失败！");
        }
        return userService.get(id);
    }

    @GetMapping("/login")
    public void login(@Validated UserInfo userInfo, HttpServletRequest request) {
        UserInfo info =  userService.login(userInfo);
        // 这个false？
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 有session的话失效掉，保证每次请求都是新的session
            session.invalidate();
        }
        request.getSession().setAttribute("user", info);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @GetMapping
    public List<UserInfo> query(String name) {
        return userService.query(name);
    }
}
