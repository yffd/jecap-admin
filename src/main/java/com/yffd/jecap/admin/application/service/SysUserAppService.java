package com.yffd.jecap.admin.application.service;

import com.alibaba.fastjson.JSON;
import com.yffd.jecap.admin.application.dto.UserLoginInfo;
import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.admin.domain.user.entity.SysUserLogin;
import com.yffd.jecap.admin.domain.user.service.SysUserLoginService;
import com.yffd.jecap.admin.domain.user.service.SysUserService;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.token.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SysUserAppService {
    @Autowired private SysUserService sysUserService;
    @Autowired private SysUserLoginService sysUserLoginService;

    public RtnResult<String> doLogin(String loginName, String loginPwd) {
        if (StringUtils.isAnyBlank(loginName, loginPwd)) return RtnResult.FAIL("账号或密码不能为空");
        SysUser user = this.sysUserService.findByAcntName(loginName);
        if (null == user) return RtnResult.FAIL("账号或密码错误");
        if (AdminConsts.DEF_DISABLED.equals(user.getAcntStatus())) return RtnResult.FAIL("账号已冻结");
        if (!this.sysUserService.checkPwd(loginPwd, user.getAcntPwd(), user.getAcntPwdSalt())) return RtnResult.FAIL("账号或密码错误");
        //登录TOKEN
        AccessToken accessToken = new AccessToken(user.getId());
        //登录信息
        UserLoginInfo loginInfo = new UserLoginInfo(user);
        SysUserLogin userLogin = new SysUserLogin();
        userLogin.setUserId(user.getId());
        userLogin.setLoginData(JSON.toJSONString(loginInfo));
        this.sysUserLoginService.save(userLogin);
        return RtnResult.OK(accessToken);
    }

    public RtnResult<String> doLogout(String tokenId) {
        if (StringUtils.isBlank(tokenId)) return RtnResult.FAIL("登出失败");
        this.sysUserLoginService.delById(tokenId);
        return RtnResult.OK("登出成功");
    }

    public RtnResult<SysUserLogin> findLoginInfo(String tokenId) {
        if (StringUtils.isBlank(tokenId)) return RtnResult.FAIL("验证失败，请重新登录");
        SysUserLogin userLogin = this.sysUserLoginService.findByUserId(tokenId);
        if (null == userLogin) return RtnResult.FAIL("验证失败，请重新登录");
        return RtnResult.OK(userLogin);
    }

    @Transactional
    public RtnResult<String> register(String loginName, String loginPwd, String confirmPwd) {
        if (StringUtils.isAnyBlank(loginName, loginPwd)) return RtnResult.FAIL("账号或密码不能为空");
        if (loginPwd.equals(confirmPwd)) return RtnResult.FAIL("密码与确认密码不一致");
        SysUser user = this.sysUserService.findByAcntName(loginName);
        if (null != user) return RtnResult.FAIL("账号已存在");
        String encryptPwd = this.sysUserService.encryptPwd(loginPwd, loginName);
        SysUser newUser = new SysUser(loginName, encryptPwd, loginName);
        this.sysUserService.add(newUser);
        return RtnResult.OK();
    }

    public RtnResult<String> addWithAccount(SysUser entity) {
        if (null == entity) return RtnResult.FAIL_PARAM_ISNULL();
        String acntName = entity.getAcntName();
        if (StringUtils.isBlank(acntName)) return RtnResult.FAIL("账号不能为空");
        SysUser user = this.sysUserService.findByAcntName(acntName);
        if (null != user) return RtnResult.FAIL_EXIST();
        String acntPwd = entity.getAcntPwd();
        String userName = entity.getUserName();
        if (StringUtils.isBlank(acntPwd)) entity.setAcntPwd(AdminConsts.DEF_LOGIN_PWD);
        if (StringUtils.isBlank(userName)) entity.setUserName(acntName);
        this.sysUserService.encryptUser(entity);
        this.sysUserService.add(entity);
        return RtnResult.OK();
    }

}
