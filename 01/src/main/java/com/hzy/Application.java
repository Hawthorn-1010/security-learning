package com.hzy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.hzy.mapper")
public class Application {

    public static void main(String[] args) {
        // 返回值：spring容器
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        System.out.println(111);
    }
}
