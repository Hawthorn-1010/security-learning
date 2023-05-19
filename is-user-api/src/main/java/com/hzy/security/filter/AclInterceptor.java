package com.hzy.security.filter;

import com.hzy.security.user.User;
import com.hzy.security.user.UserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: hzy
 * Date: 2023/5/19
 * Time: 23:13
 * Description:
 */
@Component
// 不能是Filter，因为要在审计的环节之后，AuditLogInterceptor，Filter是在Interceptor之前执行的
public class AclInterceptor extends HandlerInterceptorAdapter {

    private String[] permitUrls = new String[]{"/users/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;

        if (!ArrayUtils.contains(permitUrls, request.getRequestURI())) {
            UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
            if (userInfo == null) {
                // 401
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("text/plain");
                response.getWriter().write("need authentication");
                // 拒绝处理
                result = false;
            } else {
                // Get -> r, Post -> w
                String method = request.getMethod();
                if (!userInfo.hasPermission(method)) {
                    // 403
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("text/plain");
                    response.getWriter().write("forbidden");
                    result = false;
                }
            }
        }
        return result;
    }
}
