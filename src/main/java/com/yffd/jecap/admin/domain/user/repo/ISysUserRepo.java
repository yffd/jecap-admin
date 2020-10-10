package com.yffd.jecap.admin.domain.user.repo;

import com.yffd.jecap.admin.infrastructure.dao.user.*;

import java.util.Set;

public interface ISysUserRepo {

    ISysUserDao getUserDao();
    ISysUserGroupDao getUserGroupDao();
    ISysUserRoleDao getUserRoleDao();
    ISysUserJobDao getUserJobDao();
    ISysUserLoginDao getUserLoginDao();

    void removeRole(String userId, Set<String> roleIds);

    void removeGroup(String userId, Set<String> groupIds);

    void removeJob(String userId, Set<String> jobIds);
}
