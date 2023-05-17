package com.hzy.service;

import com.hzy.domain.ResponseResult;
import com.hzy.domain.User;

/**
 * User: hzy
 * Date: 2023/5/15
 * Time: 23:32
 * Description:
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
