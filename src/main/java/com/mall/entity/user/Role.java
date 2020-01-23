package com.mall.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role")
public class Role {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("roleName")
    private String roleName;
    @TableField("roleId")
    private String roleId;
    @TableField(value = "delFlag")
    private Boolean delFlag;
    public Integer getId() {
        return id;
    }
}
