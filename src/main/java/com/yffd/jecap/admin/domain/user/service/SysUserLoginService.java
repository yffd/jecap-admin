package com.yffd.jecap.admin.domain.user.service;

import com.yffd.jecap.admin.domain.user.entity.SysUserLogin;
import com.yffd.jecap.admin.domain.user.repo.ISysUserLoginRepo;
import com.yffd.jecap.common.base.repository.IBaseRepository;
import com.yffd.jecap.common.base.service.AbstractBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysUserLoginService extends AbstractBaseService<SysUserLogin> {
    @Autowired private ISysUserLoginRepo sysUserLoginRepo;

    @Override
    protected IBaseRepository getRepo() {
        return sysUserLoginRepo;
    }

    public void save(SysUserLogin entity) {
        if (null == entity || StringUtils.isBlank(entity.getUserId())) return;
        if (null != this.getRepo().getById(entity.getUserId())) {
            this.updateByUserId(entity);//更新
        } else {
            this.getRepo().add(entity);//添加
        }
    }

    public void updateByUserId(SysUserLogin entity) {
        if (null == entity || StringUtils.isBlank(entity.getUserId())) return;
        SysUserLogin old = new SysUserLogin();
        old.setUserId(entity.getUserId());
        this.getRepo().modify(old, entity);
    }

    public SysUserLogin findByUserId(String userId) {
        if (StringUtils.isBlank(userId)) return null;
        SysUserLogin entity = new SysUserLogin();
        entity.setUserId(userId);
        return (SysUserLogin) this.getRepo().get(entity);
    }
}
