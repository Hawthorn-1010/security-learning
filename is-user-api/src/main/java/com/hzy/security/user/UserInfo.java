package com.hzy.security.user;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

/**
 * User: hzy
 * Date: 2022/9/13
 * Time: 10:29
 * Description: 封装请求和响应
 */
public class UserInfo {
    private Integer id;

    private String name;

    @NotBlank(message = "用户名不能为null")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "密码不能为null")
    private String password;

    private String permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean hasPermission(String method) {
        boolean result = false;
        if (method.equalsIgnoreCase("get")) {
            result = StringUtils.contains(permission, "r");
        } else if (method.equalsIgnoreCase("post")) {
            result = StringUtils.contains(permission, "w");
        }
        return result;
    }
}
