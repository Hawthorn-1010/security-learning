package com.hzy;

import com.hzy.domain.User;
import com.hzy.mapper.MenuMapper;
import com.hzy.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MenuMapper menuMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void TestBCryptPasswordEncoder() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(encode);
        System.out.println(encode2);
        boolean matches = passwordEncoder.matches("1234", "$2a$10$9FpvbxidP34GFMwz9fyNlO4q1nmOAIw/oJGar2NlbhS7KfgB9NNlu");
        System.out.println(matches);
    }

    @Test
    void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    void testMenuMapper() {
        List<String> permissions = menuMapper.selectPermissionsByUserId(1L);
        System.out.println(permissions);
    }
}
