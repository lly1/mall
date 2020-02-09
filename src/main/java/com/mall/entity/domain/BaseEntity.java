package com.mall.entity.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.entity.user.User;
import com.mall.utils.UUIDGenerator;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lvlingyu
 * @Description
 * @Date 2020-01-17
 **/
@Data
public abstract class BaseEntity implements IPage, Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    protected Integer pageNo;
    @TableField(exist = false)
    protected Integer pageSize;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    protected String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("create_time")
    protected Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("update_time")
    protected Date updateTime;
    protected String createBy;
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
