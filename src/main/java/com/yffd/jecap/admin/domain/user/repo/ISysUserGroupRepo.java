package com.yffd.jecap.admin.domain.user.repo;

import com.yffd.jecap.admin.infrastructure.dao.user.ISysUserGroupDao;

public interface ISysUserGroupRepo/* extends IBaseRepository<SysUserGroup>*/ {

    ISysUserGroupDao getUserGroupDao();
}
