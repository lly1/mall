package com.mall.entity.domain;

import lombok.Data;

/**
 * @Author lly
 * @Description
 * @Date 2020-01-20
 **/
@Data
public class LoginParam {
    private String loginName;
    private String password;
    private String verifyCode;
    private String loginSrc;
}
