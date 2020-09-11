package com.mall.provider.service.pc.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.entity.pc.menu.Menu;
import com.mall.provider.dao.menu.MenuMapper;
import com.mall.provider.service.pc.menu.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> getAll() {
        return this.baseMapper.selectList(new QueryWrapper<Menu>().eq("delFlag",0));
    }

    @Override
    public List<Menu> getMenuByRole(String roleId) {
        return this.baseMapper.getMenuByRole(roleId);
    }
}
