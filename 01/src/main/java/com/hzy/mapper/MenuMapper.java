package com.hzy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzy.domain.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * User: hzy
 * Date: 2023/5/17
 * Time: 1:06
 * Description:
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermissionsByUserId(Long userId);
}
