package com.mall.entity.component;


import lombok.Data;

@Data
public class TProductComponent{
    private String id;
    private String productId;
    private String componentId;
    private Double total;
    private String delFlag;
}
