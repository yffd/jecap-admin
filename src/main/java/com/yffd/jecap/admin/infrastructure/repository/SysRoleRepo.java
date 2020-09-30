package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.role.repo.ISysRoleRepo;
import com.yffd.jecap.admin.infrastructure.dao.role.ISysRoleDao;
import com.yffd.jecap.admin.infrastructure.dao.role.ISysRoleGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleRepo implements ISysRoleRepo {
    @Autowired private ISysRoleDao roleDao;
    @Autowired private ISysRoleGroupDao roleGroupDao;

    @Override
    public ISysRoleDao getRoleDao() {
        return roleDao;
    }

    @Override
    public ISysRoleGroupDao getRoleGroupDao() {
        return roleGroupDao;
    }
}
