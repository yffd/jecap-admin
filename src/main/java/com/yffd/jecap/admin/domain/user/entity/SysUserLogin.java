package com.yffd.jecap.admin.domain.user.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-用户登录表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysUserLogin implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登录数据
     */
    private String loginData;

    /**
     * 登录方式，1=w端、2=微信、3=支付宝
     */
    private String loginType;

    /**
     * 登录IP
     */
    private String loginIp;

}
