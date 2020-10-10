package com.yffd.jecap.admin.application.assembler;

import com.alibaba.fastjson.JSON;
import com.yffd.jecap.admin.application.dto.UserLoginInfo;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.admin.domain.user.entity.SysUserLogin;

public class SysLoginAssembler {

    public static SysUserLogin buildUserLogin(SysUser user) {
        UserLoginInfo loginInfo = new UserLoginInfo(user);
        SysUserLogin userLogin = new SysUserLogin();
        userLogin.setUserId(loginInfo.getUserId());
        userLogin.setLoginData(JSON.toJSONString(loginInfo));
        return userLogin;
    }
}
