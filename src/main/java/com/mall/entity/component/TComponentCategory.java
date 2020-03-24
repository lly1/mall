package com.mall.entity.component;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class TComponentCategory {
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    protected String id;
    private String name;
    @TableField(value = "delFlag")
    private String delFlag;
    private List<TComponent> componentList;
}
