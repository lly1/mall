package com.mall.service.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.menu.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> getAll();
    List<Menu> getMenuByRole(String roleId);
}
