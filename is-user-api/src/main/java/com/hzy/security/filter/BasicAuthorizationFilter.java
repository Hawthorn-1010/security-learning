package com.hzy.security.filter;

import com.hzy.security.user.User;
import com.hzy.security.user.UserRepository;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.omg.IOP.ServiceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * User: hzy
 * Date: 2022/9/13
 * Time: 13:58
 * Description:
 */
@Component
@Order(2)
public class BasicAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (StringUtils.isNotBlank(authHeader)) {
            String token64 = StringUtils.substringAfter(authHeader, "Basic ");
            String token = new String(Base64Utils.decodeFromString(token64));
            // 直接用token.split()可能会报空指针
            String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(token, ":");

            String username = strings[0];
            String password = strings[1];

            User user = userRepository.findByUsername(username);

            if (user != null && SCryptUtil.check(password, user.getPassword())) {
                httpServletRequest.getSession().setAttribute("user", user.buildInfo());
                // 如果是通过basic方式的，每次都需要authentic，所以最后需要把session expire
                httpServletRequest.getSession().setAttribute("temp", "true");
            }
        }

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            HttpSession session = httpServletRequest.getSession();
            // 排除登录接口的
            if (session.getAttribute("temp") != null) {
                session.invalidate();
            }
        }

    }
}
