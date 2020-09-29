package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.admin.domain.role.repo.ISysRoleRepo;
import com.yffd.jecap.admin.infrastructure.dao.role.ISysRoleDao;
import com.yffd.jecap.common.base.dao.IBaseDao;
import com.yffd.jecap.common.base.repository.AbstractBaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleRepo extends AbstractBaseRepo<SysRole> implements ISysRoleRepo {
    @Autowired private ISysRoleDao sysRoleDao;

    @Override
    protected IBaseDao<SysRole> getDao() {
        return sysRoleDao;
    }

}
