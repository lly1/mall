package com.mall.service.menu.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.menu.RoleMenuMapper;
import com.mall.entity.menu.RoleMenu;
import com.mall.service.menu.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author liangjunhao
 * @Description
 * @Date 2020-02-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl  extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService{
}
