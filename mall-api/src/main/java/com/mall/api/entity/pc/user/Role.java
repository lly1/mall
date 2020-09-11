package com.mall.api.entity.pc.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.api.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author lly
 */
@Data
@TableName("sys_role")
public class Role extends BaseEntity {
    private String roleName;
    private String roleId;

    /**
     * 拥有权限菜单名称
     */
    @TableField(exist = false)
    private String authNames;
}
