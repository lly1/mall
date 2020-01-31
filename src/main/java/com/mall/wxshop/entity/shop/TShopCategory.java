package com.mall.wxshop.entity.shop;

import com.mall.entity.domain.BaseEntity;
import lombok.Data;

/**
 * @author lly
 */
@Data
public class TShopCategory extends BaseEntity {
    private String shopId;
    private Integer categoryType;
    private String categoryName;
}
