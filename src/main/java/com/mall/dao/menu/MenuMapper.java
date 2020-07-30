package com.mall.dao.menu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.api.entity.menu.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenuByRole(@Param("roleId")String roleId);
}
