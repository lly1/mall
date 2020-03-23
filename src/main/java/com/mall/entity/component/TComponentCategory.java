package com.mall.entity.component;

import lombok.Data;

import java.util.List;

@Data
public class TComponentCategory {
    private String id;
    private String name;
    private String delFlag;
    private List<TComponent> componentList;
}
