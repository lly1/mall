package com.mall.entity.domain;

import lombok.Data;
import java.io.Serializable;


@Data
public class RtnLogin implements Serializable {
    private String token;
    /**
    * redisKey 第二次登录使用
    */
    private String redisKey;
}
