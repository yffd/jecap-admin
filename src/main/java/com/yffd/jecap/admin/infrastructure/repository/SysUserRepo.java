package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.user.repo.ISysUserRepo;
import com.yffd.jecap.admin.infrastructure.dao.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class SysUserRepo implements ISysUserRepo {
    @Autowired private ISysUserDao userDao;
    @Autowired private ISysUserGroupDao userGroupDao;
    @Autowired private ISysUserRoleDao userRoleDao;
    @Autowired private ISysUserJobDao userJobDao;
    @Autowired private ISysUserLoginDao userLoginDao;

    @Override
    public ISysUserDao getUserDao() {
        return userDao;
    }

    @Override
    public ISysUserGroupDao getUserGroupDao() {
        return userGroupDao;
    }

    @Override
    public ISysUserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    @Override
    public ISysUserJobDao getUserJobDao() {
        return userJobDao;
    }

    @Override
    public ISysUserLoginDao getUserLoginDao() {
        return userLoginDao;
    }

    @Override
    public void removeRole(String userId, Set<String> roleIds) {

    }

    @Override
    public void removeGroup(String userId, Set<String> groupIds) {

    }

    @Override
    public void removeJob(String userId, Set<String> jobIds) {

    }

}
