package com.mall.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wxshop.entity.shop.TCode;
import org.apache.ibatis.annotations.Param;

public interface TCodeMapper extends BaseMapper<TCode> {
    Integer getCodeByShopId(String shopId);
    int updateCode(@Param("code") Integer code,@Param("shopId") String shopId);
}
