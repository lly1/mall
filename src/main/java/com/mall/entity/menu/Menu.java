package com.mall.entity.menu;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.entity.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_menu")
public class Menu extends BaseEntity {
    //编号
    @TableField("code")
    private String code;
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

    @TableField(exist = false)
    private List<Menu> children;
}
