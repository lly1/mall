package com.mall.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.entity.domain.BaseEntity;
import lombok.Data;

/**
 * @author lly
 */
@Data
@TableName("sys_role")
public class Role extends BaseEntity {
    private String roleName;
    private String roleId;
}
