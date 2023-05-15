package com.hzy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * User: hzy
 * Date: 2023/5/15
 * Time: 22:48
 * Description:
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 创建BCryptPasswordEncoder注入容器
     * q:直接变成了这种加密方式？
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 配置Security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问 放行接口
                .antMatchers("/user/login").anonymous()
                .antMatchers("/testConfig").hasAuthority("system:test:list")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

//        // 配置过滤器
//        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//        // 配置异常处理器
//        http.exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint)  // 认证失败处理器
//                .accessDeniedHandler(accessDeniedHandler);           // 授权失败处理器


        // 允许跨域
        http.cors();
    }
}
