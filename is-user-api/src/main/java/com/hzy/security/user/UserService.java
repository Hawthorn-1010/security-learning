package com.hzy.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: hzy
 * Date: 2022/9/13
 * Time: 13:18
 * Description:
 */
@Service
public interface UserService {
    UserInfo create(UserInfo user);

    UserInfo update(UserInfo user);

    void delete(Integer id);

    UserInfo get(Integer id);

    List<UserInfo> query(String name);

    UserInfo login(UserInfo userInfo);
}
