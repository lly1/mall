package com.mall.provider.service.pc.component.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.entity.wx.component.TComponentCategory;
import com.mall.provider.dao.component.TComponentCategoryMapper;
import com.mall.provider.service.pc.component.TComponentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lly
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TComponentCategoryServiceImpl extends ServiceImpl<TComponentCategoryMapper, TComponentCategory> implements TComponentCategoryService {
    private static Logger logger = LoggerFactory.getLogger(TComponentCategoryServiceImpl.class);

    @Override
    public List<TComponentCategory> getComponent() {
        return baseMapper.getComponent();
    }
}
