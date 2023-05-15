package com.hzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzy.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: hzy
 * Date: 2023/5/15
 * Time: 20:47
 * Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
