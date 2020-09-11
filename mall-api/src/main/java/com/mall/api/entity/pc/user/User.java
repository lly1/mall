package com.mall.api.entity.pc.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.api.entity.base.BaseEntity;
import lombok.Data;

@Data
@TableName("sys_user")
public class User extends BaseEntity {
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("phone")
    private String phone;
    /**
     * 所有权限id
     */
    @TableField(exist = false)
    private String roleId;

    /**
     * 角色名
     */
    @TableField(exist = false)
    private String roleName;

    public User(){

    }

    public User(String username){
        this.username = username;
    }
}
