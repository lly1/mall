package com.mall.provider.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.api.entity.user.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getUserRoles(String userId);

    Role getRoleNameByUserId(String userId);
}
