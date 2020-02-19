package com.mall.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wxshop.entity.shop.TCode;

public interface TCodeMapper extends BaseMapper<TCode> {
    Integer getCodeByShopId(String shopId);
    int updateCode(Integer code);
}
