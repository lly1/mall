package com.mall.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.entity.domain.BaseEntity;
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

    public User(){

    }

    public User(String username){
        this.username = username;
    }
}
