package com.mall.api.entity.component;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author lly
 */
@Data
public class TComponent{
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    protected String id;
    private String name;
    private String categoryId;
    private Double protein;
    private Double fat;
    private Double carbohydrate;
    private Double calorie;
    private Double salt;
    private Double calcium;
    private Double phosphorus;
    private Double iron;
    @TableField(value = "delFlag")
    private String delFlag;
}
