package com.mall.api.entity.menu;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.api.entity.base.BaseEntity;
import lombok.Data;

/**
 * @Author liangjunhao
 * @Description
 * @Date 2020-02-13
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu extends BaseEntity {
    private String roleId;
    private String menuId;
}
