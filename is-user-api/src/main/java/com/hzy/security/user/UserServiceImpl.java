package com.hzy.security.user;

import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: hzy
 * Date: 2022/9/13
 * Time: 13:20
 * Description:
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo create(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo, user);
        user.setPassword(SCryptUtil.scrypt(user.getPassword(), 1024, 8, 1));
        userRepository.save(user);
        userInfo.setId(user.getId());
        return userInfo;
    }

    @Override
    public UserInfo update(UserInfo user) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserInfo get(Integer id) {
        return userRepository.findById(id).get().buildInfo();
//        Optional<User> userOptional = userRepository.findById(id);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            return user.buildInfo();
//        }
//        return null;
    }

    @Override
    public List<UserInfo> query(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        UserInfo info = null;
        User user = userRepository.findByUsername(userInfo.getUsername());
        if (user != null && SCryptUtil.check(userInfo.getPassword(), user.getPassword())) {
            info = user.buildInfo();
        }
        return info;
    }
}
