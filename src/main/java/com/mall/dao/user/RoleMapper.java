package com.mall.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.entity.user.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getUserRoles(String userId);
}
