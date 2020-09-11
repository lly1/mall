package com.mall.provider.service.pc.component.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.entity.wx.component.TComponent;
import com.mall.provider.dao.component.TComponentMapper;
import com.mall.provider.service.pc.component.TComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lly
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TComponentServiceImpl extends ServiceImpl<TComponentMapper, TComponent> implements TComponentService {
    private static Logger logger = LoggerFactory.getLogger(TComponentServiceImpl.class);

}
