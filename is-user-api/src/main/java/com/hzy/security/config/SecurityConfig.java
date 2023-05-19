package com.hzy.security.config;

import com.hzy.security.filter.AclInterceptor;
import com.hzy.security.filter.AuditLogInterceptor;
import com.hzy.security.user.UserInfo;
import org.omg.IOP.ServiceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * User: hzy
 * Date: 2023/5/19
 * Time: 22:06
 * Description:
 */
@Configuration
@EnableJpaAuditing
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInterceptor auditLogInterceptor;

    @Autowired
    private AclInterceptor aclInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInterceptor);
        registry.addInterceptor(aclInterceptor);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
                HttpSession session = attributes.getRequest().getSession();
                UserInfo userInfo = (UserInfo)session.getAttribute("user");
                String username = null;
                if (userInfo != null) {
                    username = userInfo.getUsername();
                }

                return Optional.ofNullable(username);
            }
        };
    }
}
