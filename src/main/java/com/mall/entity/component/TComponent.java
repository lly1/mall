package com.mall.entity.component;

import lombok.Data;

/**
 * @author lly
 */
@Data
public class TComponent{
    private String id;
    private String name;
    private String categoryId;
    private Double protein;
    private Double fat;
    private Double carbohydrate;
    private Double calorie;
    private Double salt;
    private Double calcium;
    private Double phosphorus;
    private Double icon;
    private String delFlag;
}
