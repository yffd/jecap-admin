package com.yffd.jecap.admin.domain.role.repo;

import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.admin.domain.role.entity.SysRoleGroup;
import com.yffd.jecap.admin.domain.role.entity.SysRolePmsn;
import com.yffd.jecap.common.base.dao.IBaseDao;

import java.util.Set;

public interface ISysRoleRepo {

    IBaseDao<SysRole> getRoleDao();
    IBaseDao<SysRoleGroup> getRoleGroupDao();
    IBaseDao<SysRolePmsn> getRolePmsnDao();

    void removeGroup(String roleId, Set<String> groupIds);

    void removePmsn(String roleId, Set<String> pmsnIds);
}
