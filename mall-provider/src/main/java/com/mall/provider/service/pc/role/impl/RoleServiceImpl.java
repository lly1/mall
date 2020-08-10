package com.mall.provider.service.pc.role.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.user.RoleMapper;
import com.mall.api.entity.user.Role;
import com.mall.provider.service.pc.role.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lly
 */
@Service("RoleService")
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> getUserRoles(String userId) {
        return baseMapper.getUserRoles(userId);
    }

    @Override
    public Role getRoleNameByUserId(String userId) {
        return baseMapper.getRoleNameByUserId(userId);
    }
}
