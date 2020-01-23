package com.mall.entity.menu;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_menu")
public class Menu {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    @TableField("code")
    private String code;//编号
    @TableField("mName")
    private String mName;//菜单名称
    @TableField("mPath")
    private String mPath;//菜单映射路径
    @TableField("parentId")
    private String parentId;//父菜单ID
    @TableField("iconCls")
    private String iconCls;//图标名
    @TableField("seqNo")
    private Integer seqNo;//子菜单序列号
    @TableField(value = "delFlag")
    protected Boolean delFlag;//删除标志
    @TableField(exist = false)
    private List<Menu> children;
}
