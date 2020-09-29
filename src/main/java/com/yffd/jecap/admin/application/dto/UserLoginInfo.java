package com.yffd.jecap.admin.application.dto;

import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.common.base.valobj.IValueObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
public class UserLoginInfo implements IValueObject {
    private static final long serialVersionUID = -5989520636536630645L;
    /** 用户ID */
    private String userId;
    /** 用户名称 */
    private String userName;
    /** 登录账号名称 */
    private String acntName;

    /** 超级管理员 */
    private boolean isSuperAdmin = false;
    /** 当前登录人拥有的角色 */
    private Set<String> roles;

    public UserLoginInfo(SysUser user) {
        if (null != user) {
            this.userId = user.getId();
            this.userName = user.getUserName();
            this.acntName = user.getAcntName();
        }
    }


}
