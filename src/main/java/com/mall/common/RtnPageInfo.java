package com.mall.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author liangjunhao
 * @Description 分页查询返回类型
 * @Date 2020-01-01
 */
@Data
public class RtnPageInfo<T> implements Serializable{

    private static final long serialVersionUID = 821580315768760951L;
    //当前页数
    private int page;

    //每页显示数量
    private int pagesize;

    //总条数
    private int records;

    //数据列表
    private List<T> rows;

    //总页数
    private int total;

    //排序字段
    private String orderByField;

    //是否升序
    private boolean isAsc;


    public RtnPageInfo() {
    }

    public RtnPageInfo(Page<T> page){
        this.page = (int) page.getCurrent();
        this.pagesize = (int) page.getSize();
        this.records = (int) page.getTotal();
        this.rows = page.getRecords();
        this.total = (int) page.getPages();
//        this.orderByField = page.getOrderByField();
//        this.isAsc = page.isAsc();
    }

}
