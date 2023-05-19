package com.hzy.security.filter;

import com.hzy.security.log.AuditLog;
import com.hzy.security.log.AuditLogRepository;
import com.hzy.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * User: hzy
 * Date: 2022/11/13
 * Time: 23:44
 * Description:
 */
@Component
public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求进入的时候记录日志
        AuditLog auditLog = new AuditLog();
        auditLog.setMethod(request.getMethod());
        auditLog.setPath(request.getRequestURI());

//        User user = (User) request.getAttribute("user");
//        if (user != null) {
//            auditLog.setUsername(user.getUsername());
//        }
        auditLogRepository.save(auditLog);
        // auditLog save后会更新吗？id如何确定？创建的时候传id？
        request.setAttribute("auditLogId", auditLog.getId());
        // 如果perHandle返回false的话，请求不会被执行
        return true;
    }

    // 只有处理成功才会调用postHandle，所以这里需要调用afterCompletion
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 出去的时候记录日志
        // 有点疑惑auditLogId这个字段名
        Long auditLogId = (Long) request.getAttribute("auditLogId");
        AuditLog auditLog = auditLogRepository.findById(auditLogId).get();
        auditLog.setStatus(response.getStatus());
        auditLogRepository.save(auditLog);
    }
}
