package com.mall.service.role.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.user.UserRoleMapper;
import com.mall.entity.user.UserRole;
import com.mall.service.role.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lly
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
