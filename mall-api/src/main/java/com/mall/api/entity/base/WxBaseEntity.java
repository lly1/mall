package com.mall.api.entity.base;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.api.entity.base.IPage;
import com.mall.api.entity.base.PageUtils;
import com.mall.api.entity.pc.user.User;
import com.mall.api.utils.UUIDGenerator;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lvlingyu
 * @Description
 * @Date 2020-01-17
 **/
@Data
public abstract class WxBaseEntity implements IPage, Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    protected Integer pageNo;
    @TableField(exist = false)
    protected Integer pageSize;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    protected String id;

    @TableField(value = "create_time")
    protected Date createTime;

    @TableField(value = "update_time")
    protected Date updateTime;

    @TableField(value = "create_by")
    protected String createBy;

    @TableField(value = "update_by")
    protected String updateBy;
    /**
     * 删除标记（0：正常；1：删除；A：审核；）
     */
    @TableField(value = "delFlag")
    protected String delFlag;


    /**
     * @Author yuerfeng 14090408
     * @Description 分页参数构建
     * @Date 2020-01-17 14:38
     * @param
     * @return
     **/
    public <T> Page<T> buildPage(){
        return PageUtils.buildPage(this);
    }

    public void preInsert(User user){
        setId(UUIDGenerator.generate());
        setCreateTime(new Date());
        setCreateBy(user.getUsername());
        setDelFlag("0");
    }

    public void preUpdate(User user){
        setUpdateTime(new Date());
        setUpdateBy(user.getUsername());
    }
}
