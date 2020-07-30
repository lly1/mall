package com.mall.api.entity.base;

/**
 * @Author yuerfeng 14090408
 * @Description
 * @Date 2020-01-16
 **/
public interface IPage {

    Integer getPageNo();

    void setPageNo(Integer pageNo);

    Integer getPageSize();

    void setPageSize(Integer pageSize);
}
