package com.yffd.jecap.admin.domain.role.service;

import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.exception.AdminException;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.admin.domain.role.repo.ISysRoleRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleService {
    @Autowired private SysRoleGroupService roleGroupService;
    @Autowired private SysRolePmsnService rolePmsnService;
    @Autowired private ISysRoleRepo sysRoleRepo;
    private IBaseDao<SysRole> getDao() {
        return sysRoleRepo.getRoleDao();
    }

    public int add(SysRole role) {
        if (null == role || StringUtils.isBlank(role.getRoleName()))
            throw AdminException.cast("角色名称不能为空").prompt();
        if (StringUtils.isNotBlank(role.getRoleCode())
                && this.existByRoleCode(role.getRoleCode())) {
            throw AdminException.cast(String.format("角色编号【%s】已存在", role.getRoleCode())).prompt();
        }
        return this.getDao().addBy(role);
    }

    public int updateById(SysRole role) {
        if (null == role || StringUtils.isBlank(role.getId())) return 0;
        if (StringUtils.isNotBlank(role.getRoleCode())) {
            SysRole entity = this.findByRoleCode(role.getRoleCode());
            if (null != entity && !entity.getId().equals(role.getId())) {
                throw AdminException.cast(String.format("角色编号【%s】已存在", role.getRoleCode())).prompt();
            }
        }
        return this.getDao().modifyById(role);
    }

    @Transactional
    public void deleById(String roleId) {
        if (StringUtils.isBlank(roleId)) return;
        this.getDao().removeById(roleId);
        this.roleGroupService.delByRoleId(roleId);//删除关联关系
        this.rolePmsnService.delByRoleId(roleId);//删除关联关系
    }

    public void enableRole(String roleId) {
        this.updateStatus(roleId, AdminConsts.DEF_ENABLED);
    }

    public void disableRole(String roleId) {
        this.updateStatus(roleId, AdminConsts.DEF_DISABLED);
    }

    public boolean existByRoleCode(String roleCode) {
        return null != this.findByRoleCode(roleCode);
    }

    public SysRole findByRoleCode(String roleCode) {
        if (StringUtils.isBlank(roleCode)) return null;
        SysRole entity = new SysRole();
        entity.setRoleCode(roleCode);
        return this.getDao().findOne(entity);
    }

    private void updateStatus(String roleId, String roleStatus) {
        if (StringUtils.isAnyBlank(roleId, roleStatus)) return;
        SysRole entity = new SysRole();
        entity.setId(roleId);
        entity.setRoleStatus(roleStatus);
        this.getDao().modifyById(entity);
    }
}
