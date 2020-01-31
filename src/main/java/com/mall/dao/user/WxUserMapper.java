package com.mall.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.wxshop.entity.user.WxUserInfo;

public interface WxUserMapper extends BaseMapper<WxUserInfo> {
    WxUserInfo getWxUser(String id);
}
