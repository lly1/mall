package com.mall.provider.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.api.entity.wx.user.WxUserInfo;

public interface WxUserMapper extends BaseMapper<WxUserInfo> {
    WxUserInfo getWxUser(String id);
}
