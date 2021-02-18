package com.mall.api.dubbo.pc;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.entity.pc.menu.Menu;

import java.util.List;

/**
 * @author lly
 */
public interface DubboMenuService extends IService<Menu> {
    List<Menu> getAll();
    List<Menu> getMenuByRole(String roleId);
}
