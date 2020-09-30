package com.yffd.jecap.admin.domain.user.service;

import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.exception.AdminException;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.admin.domain.user.repo.ISysUserRepo;
import com.yffd.jecap.admin.infrastructure.dao.user.ISysUserDao;
import com.yffd.jecap.common.base.exception.DataExistException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Service
public class SysUserService {
    @Autowired private ISysUserRepo userRepo;

    private ISysUserDao getDao() {
        return this.userRepo.getUserDao();
    }

    /**
     * 添加新用户
     * @param user
     */
    public void add(SysUser user) {
        if (null == user || StringUtils.isBlank(user.getAcntName())) throw AdminException.cast("【账号名称】不能为空").prompt();
        if (this.existByAcntName(user.getAcntName())) throw DataExistException.cast("【账号名称】已存在");
        this.getDao().addBy(user);
    }

    /**
     * 删除用户
     * @param id
     */
    public void deleteById(Serializable id) {
        if (null == id) return;
        SysUser entity = new SysUser();
        entity.setId(id.toString());
        this.deleteById(entity);
    }

    /**
     * 根据用户名称查询
     * @param acntName
     * @return
     */
    public SysUser findByAcntName(String acntName) {
        SysUser entity = new SysUser();
        entity.setAcntName(acntName);
        return this.getDao().findOne(entity);
    }

    /**
     * 校验账户名称是否已存在
     * @param loginName
     * @return
     */
    public boolean existByAcntName(String loginName) {
        return null != this.findByAcntName(loginName);
    }

    /**
     * 禁用账户
     * @param userId
     */
    public void disableUser(String userId) {
        if (StringUtils.isBlank(userId)) return;
        SysUser entity = new SysUser();
        entity.setId(userId);
        entity.setAcntStatus(AdminConsts.DEF_DISABLED);
        this.getDao().modifyById(entity);
    }

    /**
     * 启用账户
     * @param userId
     */
    public void enableUser(String userId) {
        if (StringUtils.isBlank(userId)) return;
        SysUser entity = new SysUser();
        entity.setId(userId);
        entity.setAcntStatus(AdminConsts.DEF_ENABLED);
        this.getDao().modifyById(entity);
    }

    public void encryptUser(SysUser entity) {
        if (null == entity) return;
        String encryptPwd = this.encryptPwd(entity.getAcntPwd(), entity.getAcntName());
        entity.setAcntPwd(encryptPwd);
        entity.setAcntPwdSalt(entity.getAcntName());
        entity.setAcntPwdExpire(-1);
    }

    public String encryptPwd(String password, String salt) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            log.error("密码加密失败", e);
            return null;
        }
        if (StringUtils.isNotBlank(salt)) {
            digest.reset();
            digest.update(salt.getBytes());
        }
        byte[] hashedBytes = digest.digest(password.getBytes());
        int iterations = 2;
        for (int i=0; i< iterations; i++) {
            digest.reset();
            hashedBytes = digest.digest(hashedBytes);
        }
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    public boolean checkPwd(String loginPwd, String encryptPwd, String salt) {
        return encryptPwd.equals(this.encryptPwd(loginPwd, salt));
    }

}
