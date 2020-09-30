package com.yffd.jecap.admin.domain.user.repo;

import com.yffd.jecap.admin.infrastructure.dao.user.*;

public interface ISysUserRepo {

    ISysUserDao getUserDao();
    ISysUserGroupDao getUserGroupDao();
    ISysUserRoleDao getUserRoleDao();
    ISysUserJobDao getUserJobDao();
    ISysUserLoginDao getUserLoginDao();

}
