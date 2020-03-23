package com.mall.entity.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author lly
 **/
public class PageUtils {
    /**
     * @Author yuerfeng 14090408
     * @Description 参数转换
     * @Date 2020-01-17 14:38
     * @param
     * @return
     **/
    public static <T> Page<T> buildPage(IPage page){
        if(page.getPageNo() == null || page.getPageSize() == null){
            return new Page<>();
        }
        Page<T> objectPage = new Page<>();
        objectPage.setCurrent(page.getPageNo());
        objectPage.setSize(page.getPageSize());
        return objectPage;
    }
}
