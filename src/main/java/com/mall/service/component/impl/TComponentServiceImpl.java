package com.mall.service.component.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.component.TComponentMapper;
import com.mall.entity.component.TComponent;
import com.mall.service.component.TComponentService;
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
