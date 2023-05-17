package com.hzy.handler;

import com.alibaba.fastjson.JSON;
import com.hzy.domain.ResponseResult;
import com.hzy.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: hzy
 * Date: 2023/5/17
 * Time: 1:38
 * Description:
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "用户认证失败！");
        String json = JSON.toJSONString(responseResult);
        WebUtils.renderString(response, json);
    }
}
