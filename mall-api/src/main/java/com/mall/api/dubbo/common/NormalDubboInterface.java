package com.mall.api.dubbo.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 普通service的增删改查接口
 */
public interface NormalDubboInterface<T> {
    /**
     * 保存或更新
     * @param entity
     */
    boolean saveOrUpdate(T entity);

    /**
     * 批量保存或更新
     * @param entityList
     * @return
     */
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 根据id获取数据
     * @param id
     * @return
     */
    T getById(Serializable id);

    /**
     * 查询对象（对象入参）
     * @param entity
     * @return
     */
    T getOne(T entity,Map<String, Map<String,Object>> conditions);

    /**
     * 获取所有数据
     * @param entity
     * @return
     */
    List<T> list(T entity);

    /**
     * 分页获取列表数据
     * @param entity
     * @param page
     * @return
     */
    Page<T> page(T entity, Page<T> page);

    /**
     * id删除数据
     */
    boolean removeById(Serializable id);

    /**
     * 条件删除数据
     * @param entity
     */
    boolean remove(T entity);

    /**
     * 批量删除数据
     * @param idList
     * @return
     */
    boolean removeByIds(Collection<? extends Serializable> idList);
}
