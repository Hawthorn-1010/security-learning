package com.hzy.expression;

import com.hzy.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: hzy
 * Date: 2023/5/17
 * Time: 14:06
 * Description:
 */
@Component("ex")
public class HzyExpressionRoot {

    public final boolean hasAuthority(String authority) {
        // 获取当前用户所拥有的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        // permissions中是否存在authority
        return permissions.contains(authority);
    }
}
