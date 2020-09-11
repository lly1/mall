package com.mall.provider.service.pc.role.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.entity.pc.user.UserRole;
import com.mall.provider.dao.user.UserRoleMapper;
import com.mall.provider.service.pc.role.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lly
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
