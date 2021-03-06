package com.mall.api.entity.pc.menu;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.api.entity.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_menu")
public class Menu extends BaseEntity {
    //菜单名称
    @TableField("mName")
    private String mName;
    //菜单映射路径
    @TableField("mPath")
    private String mPath;
    //父菜单ID
    @TableField("parentId")
    private String parentId;
    //图标名
    @TableField("iconCls")
    private String iconCls;
    //子菜单序列号
    @TableField("seqNo")
    private Integer seqNo;
    //编号
    @TableField("code")
    private String code;

    @TableField(exist = false)
    private List<Menu> children;
}
