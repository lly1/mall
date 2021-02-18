package com.mall.api.dubbo.pc;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.pc.user.Role;

import java.util.List;

public interface DubboRoleService extends IService<Role> {
    List<Role> getUserRoles(String userId);
    Role getRoleNameByUserId(String userId);
}
