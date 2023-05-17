package com.hzy.filter;

import com.hzy.domain.LoginUser;
import com.hzy.domain.User;
import com.hzy.mapper.MenuMapper;
import com.hzy.mapper.UserMapper;
import com.hzy.utils.JwtUtil;
import com.hzy.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * User: hzy
 * Date: 2023/5/16
 * Time: 13:17
 * Description:
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 获取token。
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            // 为空，属于第一次登录
            // 放行。后面的过滤器会判断token的信息，如果没有，那也会在后面被拦截，所以这里就直接放行了
            filterChain.doFilter(request, response);
            // 避免后面的过滤器响应回来的时候，会执行下面的代码。所以需要在这里截断，直接return
            return;
        }
        // 2. 解析token获取其中的userid。
        String userId = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("token非法！");
        }

        // 3. 从redis中获取用户信息。
        LoginUser loginUser = redisCache.getCacheObject(userId);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登陆！");
        }
        // 懒得开redis
//        User user = userMapper.selectById(userId);
//        LoginUser loginUser = new LoginUser(user, menuMapper.selectPermissionsByUserId(user.getId()));

        // 4. 存入scrutinyContextHolder，因为后续的过滤器都是从这里获取认证信息的
        // 使用三个参数的构造，第三个参数代表用户已经认证成功
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }
}
