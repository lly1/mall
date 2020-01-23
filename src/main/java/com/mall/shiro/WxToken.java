package com.mall.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

@Data
public class WxToken extends UsernamePasswordToken implements Serializable {
    private String openId;
    /**
     *
     */
    private static final long serialVersionUID = 4812793519945855483L;
    @Override
    public Object getPrincipal() {
        return getOpenId();
    }

    @Override
    public Object getCredentials() {
        return "ok";
    }
    public WxToken(String openId){
        this.openId=openId;
    }
}
