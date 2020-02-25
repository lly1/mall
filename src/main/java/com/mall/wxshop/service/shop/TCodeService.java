package com.mall.wxshop.service.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wxshop.entity.shop.TCode;

/**
 * @author lly
 */
public interface TCodeService extends IService<TCode> {
    Integer getCodeByShopId(String shopId);
    int updateCode(Integer code,String shopId);
}
