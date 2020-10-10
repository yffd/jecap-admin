package com.yffd.jecap.admin.application.service;

import com.yffd.jecap.admin.application.dto.role.RoleSaveDto;
import com.yffd.jecap.admin.domain.role.service.SysRoleGroupService;
import com.yffd.jecap.admin.domain.role.service.SysRolePmsnService;
import com.yffd.jecap.admin.domain.role.service.SysRoleService;
import com.yffd.jecap.common.base.result.RtnResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
public class SysRoleAppService {
    @Autowired private SysRoleService roleService;
    @Autowired private SysRoleGroupService roleGroupService;
    @Autowired private SysRolePmsnService rolePmsnService;

    public RtnResult add(RoleSaveDto dto) {
        if (null == dto || null == dto.getRole()) return RtnResult.FAIL_PARAM_ISNULL();
        this.roleService.add(dto.getRole());
        //分配角色组
        if (CollectionUtils.isNotEmpty(dto.getGroupIds()))
            this.roleGroupService.addRoleGroup(dto.getRole().getId(), dto.getGroupIds());
        //分配权限
        if (CollectionUtils.isNotEmpty(dto.getPmsnIds()))
            this.rolePmsnService.addRolePmsn(dto.getRole().getId(), dto.getPmsnIds());
        return RtnResult.OK();
    }

    public RtnResult update(RoleSaveDto dto) {
        if (null == dto || null == dto.getRole()) return RtnResult.FAIL_PARAM_ISNULL();
        String roleId = dto.getRole().getId();
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【角色编号】不能为空");
        this.roleService.updateById(dto.getRole());
        //更新角色组
        if (CollectionUtils.isNotEmpty(dto.getGroupIds()))
            this.roleService.saveGroup(roleId, dto.getGroupIds());
        //更新权限
        if (CollectionUtils.isNotEmpty(dto.getPmsnIds())) {
            this.roleService.savePmsn(roleId, dto.getPmsnIds());
        }
        return RtnResult.OK();
    }

    public RtnResult delRoleGroup(String roleId, Set<String> groupIds) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL_PARAM_ISNULL();
        if (CollectionUtils.isEmpty(groupIds)) {
            this.roleGroupService.delByRoleId(roleId);
        } else {
            this.roleGroupService.delBy(roleId, groupIds);
        }
        return RtnResult.OK();
    }

    public RtnResult delRolePmsn(String roleId, Set<String> pmsnIds) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL_PARAM_ISNULL();
        if (CollectionUtils.isEmpty(pmsnIds)) {
            this.rolePmsnService.delByRoleId(roleId);
        } else {
            this.rolePmsnService.delBy(roleId, pmsnIds);
        }
        return RtnResult.OK();
    }


}
