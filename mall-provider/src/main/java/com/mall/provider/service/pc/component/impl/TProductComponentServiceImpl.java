package com.mall.provider.service.pc.component.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.entity.wx.component.TProductComponent;
import com.mall.provider.dao.component.TProductComponentMapper;
import com.mall.provider.service.pc.component.TProductComponentService;
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
public class TProductComponentServiceImpl extends ServiceImpl<TProductComponentMapper, TProductComponent> implements TProductComponentService {
    private static Logger logger = LoggerFactory.getLogger(TProductComponentServiceImpl.class);

    @Override
    public List<TProductComponent> findByProductId(String productId) {
        return baseMapper.findByProductId(productId);
    }
}
