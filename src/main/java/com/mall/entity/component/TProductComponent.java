package com.mall.entity.component;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TProductComponent{
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    protected String id;
    private String productId;
    private String componentId;
    @TableField(exist = false)
    private String componentName;
    private Double total;
    @TableField(value = "delFlag")
    private String delFlag;
}
