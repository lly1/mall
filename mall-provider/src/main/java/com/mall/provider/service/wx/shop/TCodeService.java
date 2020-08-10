package com.mall.provider.service.wx.shop;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wx.entity.shop.TCode;

/**
 * @author lly
 */
public interface TCodeService extends IService<TCode> {
    Integer getCodeByShopId(String shopId);
    int updateCode(Integer code,String shopId);
}
