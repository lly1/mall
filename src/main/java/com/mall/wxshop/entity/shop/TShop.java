package com.mall.wxshop.entity.shop;

import com.mall.entity.domain.BaseEntity;
import lombok.Data;


/**
 * @author lly
 */
@Data
public class TShop extends BaseEntity {
    private String userId;
    private String shopName;
    private String shopLogo;
    private String shopInfo;
    private Double shopStar;
    private String shopState;
    private String openTime;
    private String longitude;
    private String latitude;
}
