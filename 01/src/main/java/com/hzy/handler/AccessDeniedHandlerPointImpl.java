package com.hzy.handler;

import com.alibaba.fastjson.JSON;
import com.hzy.domain.ResponseResult;
import com.hzy.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AccessDeniedHandlerPointImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult<>(HttpStatus.FORBIDDEN.value(), "用户授权不足！");
        String json = JSON.toJSONString(responseResult);
        WebUtils.renderString(response, json);
    }
}
