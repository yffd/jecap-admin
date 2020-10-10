package com.yffd.jecap.admin.domain.role.service;

import com.yffd.jecap.admin.domain.role.entity.SysRoleGroup;
import com.yffd.jecap.admin.domain.role.repo.ISysRoleRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleGroupService {
    @Autowired private ISysRoleRepo roleRepo;

    private IBaseDao<SysRoleGroup> getDao() {
        return roleRepo.getRoleGroupDao();
    }

    /**
     * 查找关系
     * @param groupId
     * @return
     */
    public Set<String> queryRoleIds(String groupId) {
        if (StringUtils.isBlank(groupId)) return Collections.emptySet();
        SysRoleGroup entity = new SysRoleGroup(null, groupId);
        List<SysRoleGroup> list = this.getDao().queryList(entity);
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getRoleId()));
        return ids;
    }

    /**
     * 查找关系
     * @param roleId
     * @return
     */
    public Set<String> queryGroupIds(String roleId) {
        if (StringUtils.isBlank(roleId)) return Collections.emptySet();
        SysRoleGroup entity = new SysRoleGroup(roleId, null);
        List<SysRoleGroup> list = this.getDao().queryList(entity);
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getGroupId()));
        return ids;
    }

    /**
     * 添加或解除关系
     * @param roleId
     * @param addGroupIds
     * @param delGroupIds
     */
    @Transactional
    public void addAndDel(String roleId, Set<String> addGroupIds, Set<String> delGroupIds) {
        if (StringUtils.isBlank(roleId)) return;
        if (CollectionUtils.isNotEmpty(delGroupIds)) delGroupIds.forEach(groupId -> this.delBy(roleId, groupId));
        if (CollectionUtils.isNotEmpty(addGroupIds)) addGroupIds.forEach(groupId -> this.addRoleGroup(roleId, groupId));
    }

    /**
     * 添加关系
     * @param roleId
     * @param groupId
     */
    public void addRoleGroup(String roleId, String groupId) {
        if (StringUtils.isAnyBlank(roleId, groupId)) return;
        SysRoleGroup entity = new SysRoleGroup(roleId, groupId);
        if (null != this.getDao().queryOne(entity)) return;//已分配
        this.getDao().addBy(entity);
    }

    /**
     * 添加关系
     * @param roleId
     * @param groupIds
     */
    @Transactional
    public void addRoleGroup(String roleId, Set<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) return;
        groupIds.forEach(groupId -> this.addRoleGroup(roleId, groupId));
    }

    /**
     * 添加关系
     * @param roleIds
     * @param groupId
     */
    @Transactional
    public void addRoleGroup(Set<String> roleIds, String groupId) {
        if (CollectionUtils.isEmpty(roleIds)) return;
        roleIds.forEach(tmp -> this.addRoleGroup(tmp, groupId));
    }

    /**
     * 删除关系
     * @param roleId
     * @param groupId
     */
    public void delBy(String roleId, String groupId) {
        if (StringUtils.isAnyBlank(roleId, groupId)) return;
        SysRoleGroup entity = new SysRoleGroup(roleId, groupId);
        this.getDao().removeBy(entity);
    }

    /**
     * 删除关系
     * @param roleId
     * @param groupIds
     */
    public void delBy(String roleId, Set<String> groupIds) {
        if (StringUtils.isBlank(roleId) || CollectionUtils.isEmpty(groupIds)) return;
        this.roleRepo.removeGroup(roleId, groupIds);
    }

    /**
     * 删除关系
     * @param roleId
     */
    public void delByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) return;
        this.getDao().removeBy(new SysRoleGroup(roleId, null));
    }

    /**
     * 删除关系
     * @param groupId
     */
    public void delByGroupId(String groupId) {
        if (StringUtils.isBlank(groupId)) return;
        this.getDao().removeBy(new SysRoleGroup(null, groupId));
    }

}
