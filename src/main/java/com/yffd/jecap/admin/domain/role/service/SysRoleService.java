package com.yffd.jecap.admin.domain.role.service;

import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.admin.domain.role.repo.ISysRoleRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
import com.yffd.jecap.common.base.page.PageData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
            throw SysException.cast("角色名称不能为空").prompt();
        if (StringUtils.isNotBlank(role.getRoleCode())
                && this.existByRoleCode(role.getRoleCode())) {
            throw SysException.cast(String.format("角色编号【%s】已存在", role.getRoleCode())).prompt();
        }
        return this.getDao().addBy(role);
    }

    public int updateById(SysRole role) {
        if (null == role || StringUtils.isBlank(role.getId())) return 0;
        if (StringUtils.isNotBlank(role.getRoleCode())) {
            SysRole entity = this.queryByRoleCode(role.getRoleCode());
            if (null != entity && !entity.getId().equals(role.getId())) {
                throw SysException.cast(String.format("角色编号【%s】已存在", role.getRoleCode())).prompt();
            }
        }
        return this.getDao().modifyById(role);
    }

    @Transactional
    public void deleteById(String roleId) {
        if (StringUtils.isBlank(roleId)) return;
        this.getDao().removeById(roleId);
        this.roleGroupService.delByRoleId(roleId);//删除关联关系
        this.rolePmsnService.delByRoleId(roleId);//删除关联关系
    }

    public SysRole queryById(String roleId) {
        if (StringUtils.isBlank(roleId)) return null;
        return this.getDao().queryById(roleId);
    }

    public PageData<SysRole> queryPage(SysRole role, int pageNum, int pageSize) {
        return this.getDao().queryPage(role, pageNum, pageSize);
    }

    public void enable(String roleId) {
        this.updateStatus(roleId, "1");
    }

    public void disable(String roleId) {
        this.updateStatus(roleId, "0");
    }

    public boolean existByRoleCode(String roleCode) {
        return null != this.queryByRoleCode(roleCode);
    }

    public SysRole queryByRoleCode(String roleCode) {
        if (StringUtils.isBlank(roleCode)) return null;
        SysRole entity = new SysRole();
        entity.setRoleCode(roleCode);
        return this.getDao().queryOne(entity);
    }

    public void updateStatus(String roleId, String roleStatus) {
        if (StringUtils.isAnyBlank(roleId, roleStatus)) return;
        SysRole entity = new SysRole();
        entity.setId(roleId);
        entity.setRoleStatus(roleStatus);
        this.getDao().modifyById(entity);
    }

    @Transactional
    public void saveGroup(String roleId, Set<String> groupIds) {
        if (StringUtils.isBlank(roleId) || CollectionUtils.isEmpty(groupIds)) return;
        this.roleGroupService.delByRoleId(roleId);
        this.roleGroupService.addRoleGroup(roleId, groupIds);
    }

    @Transactional
    public void savePmsn(String roleId, Set<String> pmsnIds) {
        if (StringUtils.isBlank(roleId) || CollectionUtils.isEmpty(pmsnIds)) return;
        this.rolePmsnService.delByRoleId(roleId);
        this.rolePmsnService.addRolePmsn(roleId, pmsnIds);
    }
}
