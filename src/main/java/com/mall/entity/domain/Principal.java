package com.mall.entity.domain;

import com.mall.entity.user.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lly
 * @copyright :@2018
 */
@Data
public class Principal implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String id;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 姓名
     */
    private String name;

    /**
     * 登录来源，新增加
     */
    private String loginSrc;

    /**
     * SESSIONID
     */
    private String sessionId;

    /**
    * 用户类型
    */
    private Integer userType;

    public Principal(User user, String loginSrc) {
        this.id = user.getId();
        this.loginName = user.getUsername();
        this.name = user.getUsername();
        this.loginSrc = loginSrc;
        this.userType = user.getRoleId();
    }

    public Principal(){}
}
