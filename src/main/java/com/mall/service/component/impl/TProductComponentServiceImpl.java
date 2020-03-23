package com.mall.service.component.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.dao.component.TProductComponentMapper;
import com.mall.entity.component.TProductComponent;
import com.mall.service.component.TProductComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author lly
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TProductComponentServiceImpl extends ServiceImpl<TProductComponentMapper, TProductComponent> implements TProductComponentService {
    private static Logger logger = LoggerFactory.getLogger(TProductComponentServiceImpl.class);

}
