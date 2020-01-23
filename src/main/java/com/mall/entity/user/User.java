package com.mall.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class User {
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("roleId")
    private Integer roleId;
    @TableField("phone")
    private String phone;
    @TableField("roleName")
    private String roleName;
    /**
     * 删除标记（0：正常；1：删除；A：审核；）
     */
    @TableField(value = "delFlag")
    protected Boolean delFlag;
    @TableField(exist = false)
    private String sessionId;

}
