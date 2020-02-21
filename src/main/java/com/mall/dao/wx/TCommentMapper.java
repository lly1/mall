package com.mall.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.wxshop.entity.sale.TComment;
import org.apache.ibatis.annotations.Param;


public interface TCommentMapper extends BaseMapper<TComment> {
    Page<TComment> findCommentByShopId(@Param("shopId") String shopId, Page<TComment> page);
}
