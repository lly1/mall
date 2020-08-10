package com.mall.provider.service.wx.sale;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.wx.entity.sale.TComment;


/**
 * @author lly
 */
public interface TCommentService extends IService<TComment> {
    Page<TComment> findCommentByShopId(String shopId, Page<TComment> page);
}
