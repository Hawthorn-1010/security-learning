package com.hzy.service.impl;

import com.hzy.domain.LoginUser;
import com.hzy.domain.ResponseResult;
import com.hzy.domain.User;
import com.hzy.service.LoginService;
import com.hzy.utils.JwtUtil;
import com.hzy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * User: hzy
 * Date: 2023/5/15
 * Time: 23:32
 * Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        // 获取AuthenticationManager的authenticate方法进行验证
        // 会调用到UserDetailServiceImpl
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // 如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            return new ResponseResult(403, "登陆失败！");
        }

        // 如果认证通过，使用userid生成jwt，jwt存入ResponseResult返回
        // UserDetailServiceImpl中返回的LoginUser
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        // 只是用id进行生成
        String jwt = JwtUtil.createJWT(id);

        // 使返回的body中有：token：jwt 的键值对
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);

        // 把完整的用户信息存入redis userid:用户信息
        redisCache.setCacheObject(id, loginUser);

        return new ResponseResult(200, "登陆成功", map);
    }

    @Override
    public ResponseResult logout() {
        // 没有参数，但是每次访问都会经过过滤器获取到相关的loginUser的信息
        // 1. 获取SecurityContextHolder中的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 2. 删除redis中对应的值
        String userId = loginUser.getUser().getId().toString();
        redisCache.deleteObject(userId);

        return new ResponseResult(200, "注销成功");
    }
}
