package com.yffd.jecap.admin.domain.role.service;

import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.exception.AdminException;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.admin.domain.role.repo.ISysRoleRepo;
import com.yffd.jecap.common.base.repository.IBaseRepository;
import com.yffd.jecap.common.base.service.AbstractBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService extends AbstractBaseService<SysRole> {
    @Autowired private ISysRoleRepo sysRoleRepo;

    @Override
    protected IBaseRepository getRepo() {
        return sysRoleRepo;
    }

    @Override
    public int addBy(SysRole entity) {
        if (null == entity || StringUtils.isBlank(entity.getRoleName()))
            throw AdminException.cast("角色名称不能为空").prompt();
        if (StringUtils.isNotBlank(entity.getRoleCode())
                && this.existByRoleCode(entity.getRoleCode())) {
            throw AdminException.cast(String.format("角色编号【%s】已存在", entity.getRoleCode())).prompt();
        }
        return this.sysRoleRepo.add(entity);
    }

    @Override
    public int updateById(SysRole entity) {
        if (null == entity || StringUtils.isBlank(entity.getId())) return 0;
        entity.setRoleCode(null);//角色编号不允许修改
        return this.getRepo().modifyById(entity);
    }

    public void enableRole(String roleId) {
        this.updateStatus(roleId, AdminConsts.DEF_ENABLED);
    }

    public void disableRole(String roleId) {
        this.updateStatus(roleId, AdminConsts.DEF_DISABLED);
    }

    public boolean existByRoleCode(String roleCode) {
        if (StringUtils.isBlank(roleCode)) throw AdminException.cast().prompt("角色编号不能为空");
        SysRole entity = new SysRole();
        entity.setRoleCode(roleCode);
        return null != this.findBy(entity);
    }

    private void updateStatus(String roleId, String roleStatus) {
        if (StringUtils.isAnyBlank(roleId, roleStatus)) return;
        SysRole entity = new SysRole();
        entity.setId(roleId);
        entity.setRoleStatus(roleStatus);
        this.getRepo().modifyById(entity);
    }
}
