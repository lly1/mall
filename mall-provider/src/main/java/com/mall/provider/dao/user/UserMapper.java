package com.mall.provider.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.api.entity.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> getUserByRoleId(@Param("roleId")Integer roleId);
}
