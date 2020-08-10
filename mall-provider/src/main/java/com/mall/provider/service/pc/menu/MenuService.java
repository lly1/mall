package com.mall.provider.service.pc.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.menu.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<Menu> getAll();
    List<Menu> getMenuByRole(String roleId);
}
