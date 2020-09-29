package com.yffd.jecap.admin.application.service;

import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.login.model.entity.LoginInfo;
import com.yffd.jecap.admin.domain.login.model.valobj.UserLoginInfo;
import com.yffd.jecap.admin.domain.login.service.LoginInfoService;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.admin.domain.user.service.SysUserService;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.service.AbstractBaseAppService;
import com.yffd.jecap.common.base.service.IBaseService;
import com.yffd.jecap.common.base.token.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SysUserAppService extends AbstractBaseAppService<SysUser> {
    @Autowired private LoginInfoService loginInfoService;

    @Autowired private SysUserService sysUserService;
    @Override
    protected IBaseService getBaseService() {
        return sysUserService;
    }

    public RtnResult<String> doLogin(String loginName, String loginPwd) {
        if (StringUtils.isAnyBlank(loginName, loginPwd)) return RtnResult.FAIL("账号或密码不能为空");
        SysUser user = this.sysUserService.findByAcntName(loginName);
        if (null == user) return RtnResult.FAIL("账号或密码错误");
        if (AdminConsts.DEF_DISABLED.equals(user.getUserStatus())) return RtnResult.FAIL("账号已冻结");
        if (!this.sysUserService.checkPwd(loginPwd, user.getLoginPwd(), user.getLoginPwdSalt())) return RtnResult.FAIL("账号或密码错误");
        //登录成功
        AccessToken accessToken = new AccessToken(user.getId());
        this.loginInfoService.save(new UserLoginInfo(user));
        return RtnResult.OK(accessToken);
    }

    public RtnResult<String> doLogout(String tokenId) {
        if (StringUtils.isBlank(tokenId)) return RtnResult.FAIL("登出失败");
        this.loginInfoService.delete(tokenId);
        return RtnResult.OK("登出成功");
    }

    public RtnResult<LoginInfo> findLoginInfo(String tokenId) {
        if (StringUtils.isBlank(tokenId)) return RtnResult.FAIL("验证失败，请重新登录");
        UserLoginInfo loginInfo = this.loginInfoService.findByUserId(tokenId);
        if (null == loginInfo) return RtnResult.FAIL("验证失败，请重新登录");
        return RtnResult.OK(loginInfo);
    }

    @Transactional
    public RtnResult<String> register(String loginName, String loginPwd, String confirmPwd) {
        if (StringUtils.isAnyBlank(loginName, loginPwd)) return RtnResult.FAIL("账号或密码不能为空");
        if (loginPwd.equals(confirmPwd)) return RtnResult.FAIL("密码与确认密码不一致");
        SysUser user = this.sysUserService.findByAcntName(loginName);
        if (null != user) return RtnResult.FAIL("账号已存在");
        String encryptPwd = this.sysUserService.encryptPwd(loginPwd, loginName);
        SysUser newUser = new SysUser(loginName, encryptPwd, loginName);
        this.sysUserService.addBy(newUser);
        return RtnResult.OK();
    }

    public RtnResult<String> addWithAccount(SysUser entity) {
        if (null == entity) return RtnResult.FAIL_PARAM_ISNULL();
        String loginName = entity.getLoginName();
        if (StringUtils.isBlank(loginName)) return RtnResult.FAIL("账号不能为空");
        SysUser user = this.sysUserService.findByAcntName(loginName);
        if (null != user) return RtnResult.FAIL_EXIST();
        String loginPwd = entity.getLoginPwd();
        String userName = entity.getUserName();
        if (StringUtils.isBlank(loginPwd)) entity.setLoginPwd(AdminConsts.DEF_LOGIN_PWD);
        if (StringUtils.isBlank(userName)) entity.setUserName(loginName);
        this.sysUserService.encryptUser(entity);
        this.sysUserService.addBy(entity);
        return RtnResult.OK();
    }

}
