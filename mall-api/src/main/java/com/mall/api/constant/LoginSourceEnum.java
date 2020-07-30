package com.mall.api.constant;

/**
 * 登录的来源
 * @author
 */
public enum LoginSourceEnum {
    /**
     * pc端
     */
    PC("PC端", "pc"),

    /**
     * 钉钉端
     */
    WX("微信小程序", "wx");

    private String name;
    private String index;

    LoginSourceEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
