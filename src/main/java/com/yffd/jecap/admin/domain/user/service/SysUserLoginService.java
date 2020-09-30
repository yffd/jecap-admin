package com.yffd.jecap.admin.domain.user.service;

import com.yffd.jecap.admin.domain.user.entity.SysUserLogin;
import com.yffd.jecap.admin.domain.user.repo.ISysUserRepo;
import com.yffd.jecap.admin.infrastructure.dao.user.ISysUserLoginDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysUserLoginService {
    @Autowired private ISysUserRepo userRepo;

    private ISysUserLoginDao getDao() {
        return this.userRepo.getUserLoginDao();
    }

    public void save(SysUserLogin entity) {
        if (null == entity || StringUtils.isBlank(entity.getUserId())) return;
        if (null != this.getDao().findById(entity.getUserId())) {
            this.updateByUserId(entity);//更新
        } else {
            this.getDao().addBy(entity);//添加
        }
    }

    public void updateByUserId(SysUserLogin entity) {
        if (null == entity || StringUtils.isBlank(entity.getUserId())) return;
        SysUserLogin old = new SysUserLogin();
        old.setUserId(entity.getUserId());
        this.getDao().modifyBy(old, entity);
    }

    public void delById(String id) {
        if (StringUtils.isBlank(id)) return;
        this.getDao().removeById(id);
    }

    public SysUserLogin findByUserId(String userId) {
        if (StringUtils.isBlank(userId)) return null;
        SysUserLogin entity = new SysUserLogin();
        entity.setUserId(userId);
        return (SysUserLogin) this.getDao().findOne(entity);
    }


}
