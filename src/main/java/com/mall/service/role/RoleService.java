package com.mall.service.role;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.user.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> getUserRoles(String userId);

    Role getRoleNameByUserId(String userId);
}
