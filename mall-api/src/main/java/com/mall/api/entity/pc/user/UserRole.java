package com.mall.api.entity.pc.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.api.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author lly
 */
@Data
@TableName("sys_user_role")
public class UserRole extends BaseEntity {
    private String userId;
    private String roleId;
}
