package com.hzy.security.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * User: hzy
 * Date: 2022/9/12
 * Time: 23:46
 * Description:
 */
public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User, Integer> {
    List<UserInfo> findByName(String name);

    User findByUsername(String username);
}
