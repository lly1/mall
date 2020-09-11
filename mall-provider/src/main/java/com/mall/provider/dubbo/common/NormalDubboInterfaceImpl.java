package com.mall.provider.dubbo.common;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.api.dubbo.common.NormalDubboInterface;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 普通service的增删改查实现
 * @author lly
 */
public class NormalDubboInterfaceImpl<S extends IService<T>, T> implements NormalDubboInterface<T>{
    protected Log log = LogFactory.getLog(this.getClass());
    @Autowired
    protected S service;

    public NormalDubboInterfaceImpl() {
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        Assert.notNull(entity,"参数错误!");
        return service.saveOrUpdate(entity);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        Assert.notNull(entityList,"参数错误!");
        return service.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    public T getById(Serializable id) {
        Assert.notNull(id,"参数错误!");
        return service.getById(id);
    }

    @Override
    public T getOne(T entity,Map<String,Map<String,Object>> conditions) {
        Assert.notNull(entity,"参数错误!");
        Wrapper<T> wrapper = new QueryWrapper<T>();
        conditions.forEach((key,value)->{
            Class<?> clazz = wrapper.getClass();
            try {
                Method method = clazz.getMethod(key);
                value.forEach((column,val)->{
                    try {
                        method.invoke(wrapper,column,val);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
        return service.getOne(wrapper);
    }

    @Override
    public List<T> list(T entity) {
        return null;
    }

    @Override
    public Page<T> page(T entity, Page<T> page) {
        return null;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public boolean remove(T entity) {
        return false;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return false;
    }
}
