package com.yffd.jecap.admin.application.service;

import com.yffd.jecap.admin.application.assembler.SysLoginAssembler;
import com.yffd.jecap.admin.application.dto.user.UserSaveDto;
import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.admin.domain.user.entity.SysUserLogin;
import com.yffd.jecap.admin.domain.user.service.*;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.token.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
public class SysUserAppService {
    @Autowired private SysUserService userService;
    @Autowired private SysUserLoginService userLoginService;
    @Autowired private SysUserRoleService userRoleService;
    @Autowired private SysUserGroupService userGroupService;
    @Autowired private SysUserJobService userJobService;

    public RtnResult<String> add(UserSaveDto dto) {
        if (null == dto || null == dto.getUser()) return RtnResult.FAIL_PARAM_ISNULL();
        this.userService.add(dto.getUser());
        //分配角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIds()))
            this.userRoleService.addUserRole(dto.getUser().getId(), dto.getRoleIds());
        //分配用户组
        if (CollectionUtils.isNotEmpty(dto.getGroupIds()))
            this.userGroupService.addUserGroup(dto.getUser().getId(), dto.getGroupIds());
        //分配岗位
        if (CollectionUtils.isNotEmpty(dto.getJobIds()))
            this.userJobService.addUserJob(dto.getUser().getId(), dto.getJobIds());
        return RtnResult.OK();
    }

    public RtnResult<String> add(SysUser user) {
        this.userService.add(user);
        return RtnResult.OK();
    }

    @Transactional
    public RtnResult<String> addWithRole(SysUser user, String roleCode) {
        this.userService.add(user);
        if (StringUtils.isBlank(roleCode)) return RtnResult.OK();
        this.userService.rltRole(roleCode, user.getId());
        return RtnResult.OK();
    }

    @Transactional
    public RtnResult addWithRole(SysUser user, Set<String> roleCodes) {
        this.userService.add(user);
        if (CollectionUtils.isEmpty(roleCodes)) return RtnResult.OK();
        roleCodes.forEach(roleCode -> this.userService.rltRole(user.getId(), roleCode));
        return RtnResult.OK();
    }

    public RtnResult add(String acntName, String acntPwd, String acntType) {
        this.userService.add(new SysUser(acntName, acntPwd, acntType));
        return RtnResult.OK();
    }

    public RtnResult addWithRole(String acntName, String acntPwd, String acntType, String roleCode) {
        this.addWithRole(new SysUser(acntName, acntPwd, acntType), roleCode);
        return RtnResult.OK();
    }

    public RtnResult update(UserSaveDto dto) {
        if (null == dto || null == dto.getUser()) return RtnResult.FAIL_PARAM_ISNULL();
        String userId = dto.getUser().getId();
        if (StringUtils.isBlank(userId)) return RtnResult.FAIL("用户ID不能为空");
        this.userService.updateById(dto.getUser());
        //更新角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIds())) {
            this.userRoleService.delByUserId(userId);
            this.userRoleService.addUserRole(userId, dto.getRoleIds());
        }
        //更新用户组
        if (CollectionUtils.isNotEmpty(dto.getGroupIds())) {
            this.userGroupService.delByUserId(userId);
            this.userGroupService.addUserGroup(userId, dto.getGroupIds());
        }
        //更新岗位
        if (CollectionUtils.isNotEmpty(dto.getJobIds())) {
            this.userJobService.delByUserId(userId);
            this.userJobService.addUserJob(userId, dto.getJobIds());
        }
        return RtnResult.OK();
    }

    @Transactional
    public RtnResult deleteById(String userId) {
        if (StringUtils.isBlank(userId)) return RtnResult.FAIL_PARAM_ISNULL();
        this.userService.deleteById(userId);
        return RtnResult.OK();
    }

    public RtnResult queryById(String userId) {
        return RtnResult.OK(this.userService.queryById(userId));
    }

    public RtnResult queryPage(SysUser user, int pageNum, int pageSize) {
        return RtnResult.OK(this.userService.queryPage(user, pageNum, pageSize));
    }

    public RtnResult enable(String userId) {
        this.userService.enableUser(userId);
        return RtnResult.OK();
    }

    public RtnResult disable(String userId) {
        this.userService.disableUser(userId);
        return RtnResult.OK();
    }

    public void resetPwd(String userId, String newPwd) {
        SysUser user = this.userService.queryById(userId);
        if (null == user) throw SysException.cast("该用户已不存在").prompt();
        this.userService.updatePwd(user.getAcntName(), newPwd);
    }

    public RtnResult<String> doLogin(String acntName, String acntPwd) {
        if (StringUtils.isAnyBlank(acntName, acntPwd)) return RtnResult.FAIL("账号或密码不能为空");
        SysUser user = this.userService.queryByAcntName(acntName);
        if (null == user) return RtnResult.FAIL("账号或密码错误");
        if ("0".equals(user.getAcntStatus())) return RtnResult.FAIL("账号已冻结");
        if (!this.userService.checkPwd(acntPwd, user.getAcntPwd(), user.getAcntPwdSalt())) return RtnResult.FAIL("账号或密码错误");
        //登录TOKEN
        AccessToken accessToken = new AccessToken(user.getId());
        //登录信息
        this.userLoginService.save(SysLoginAssembler.buildUserLogin(user));
        return RtnResult.OK(accessToken);
    }

    public RtnResult<String> doLogout(String tokenId) {
        if (StringUtils.isBlank(tokenId)) return RtnResult.FAIL("登出失败");
        this.userLoginService.delById(tokenId);
        return RtnResult.OK("登出成功");
    }

    public RtnResult<SysUserLogin> queryLoginInfo(String tokenId) {
        if (StringUtils.isBlank(tokenId)) return RtnResult.FAIL("验证失败，请重新登录");
        SysUserLogin userLogin = this.userLoginService.queryByUserId(tokenId);
        if (null == userLogin) return RtnResult.FAIL("验证失败，请重新登录");
        return RtnResult.OK(userLogin);
    }

    @Transactional
    public RtnResult<String> register(String acntName, String acntPwd, String confirmPwd) {
        if (StringUtils.isAnyBlank(acntName, acntPwd)) return RtnResult.FAIL("账号或密码不能为空");
        if (acntPwd.equals(confirmPwd)) return RtnResult.FAIL("密码与确认密码不一致");
        SysUser user = this.userService.queryByAcntName(acntName);
        if (null != user) return RtnResult.FAIL("账号已存在");
        String encryptPwd = this.userService.encryptPwd(acntPwd, acntName);
        SysUser newUser = new SysUser(acntName, encryptPwd, acntName);
        this.userService.add(newUser);
        return RtnResult.OK();
    }

    /**
     * 删除用户拥有的角色
     * @param userId
     * @param roleIds 如果为空，则删除用户拥有的所有角色
     * @return
     */
    public RtnResult delUserRole(String userId, Set<String> roleIds) {
        if (StringUtils.isBlank(userId)) return RtnResult.FAIL_PARAM_ISNULL();
        if (CollectionUtils.isEmpty(roleIds)) {
            this.userRoleService.delByUserId(userId);
        } else {
            this.userRoleService.delBy(userId, roleIds);
        }
        return RtnResult.OK();
    }

    /**
     * 删除用户所在的组
     * @param userId
     * @param groupIds 如果为空，则删除用户所在的所有组
     * @return
     */
    @Transactional
    public RtnResult<String> delUserGroup(String userId, Set<String> groupIds) {
        if (StringUtils.isBlank(userId)) return RtnResult.FAIL_PARAM_ISNULL();
        if (CollectionUtils.isEmpty(groupIds)) {
            this.userGroupService.delByUserId(userId);
        } else {
            this.userGroupService.delBy(userId, groupIds);
        }
        return RtnResult.OK();
    }

    /**
     * 删除用户的岗位
     * @param userId
     * @param jobIds    如果为空，则删除用户的所有岗位
     * @return
     */
    public RtnResult delUserJob(String userId, Set<String> jobIds) {
        if (StringUtils.isBlank(userId)) return RtnResult.FAIL_PARAM_ISNULL();
        if (CollectionUtils.isEmpty(jobIds)) {
            this.userJobService.delByUserId(userId);
        } else {
            this.userJobService.delBy(userId, jobIds);
        }
        return RtnResult.OK();
    }


}
