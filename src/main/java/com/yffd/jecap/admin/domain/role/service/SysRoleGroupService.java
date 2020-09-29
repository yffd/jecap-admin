package com.yffd.jecap.admin.domain.role.service;

import com.yffd.jecap.admin.domain.role.entity.SysRoleGroup;
import com.yffd.jecap.admin.domain.role.repo.ISysRoleGroupRepo;
import com.yffd.jecap.common.base.repository.IBaseRepository;
import com.yffd.jecap.common.base.service.AbstractBaseService;
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
public class SysRoleGroupService extends AbstractBaseService<SysRoleGroup> {
    @Autowired private ISysRoleGroupRepo sysRoleGroupRepo;

    @Override
    protected IBaseRepository getRepo() {
        return sysRoleGroupRepo;
    }

    /**
     * 查找关系
     * @param groupId
     * @return
     */
    public Set<String> findRoleIds(String groupId) {
        if (StringUtils.isBlank(groupId)) return Collections.emptySet();
        SysRoleGroup entity = new SysRoleGroup(null, groupId);
        List<SysRoleGroup> list = this.getRepo().getList(entity);
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getGroupId()));
        return ids;
    }

    /**
     * 查找关系
     * @param roleId
     * @return
     */
    public Set<String> findGroupIds(String roleId) {
        if (StringUtils.isBlank(roleId)) return Collections.emptySet();
        SysRoleGroup entity = new SysRoleGroup(roleId, null);
        List<SysRoleGroup> list = this.getRepo().getList(entity);
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getRoleId()));
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
        if (null != this.getRepo().get(entity)) return;//已分配
        this.getRepo().add(entity);
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
     * @param groupIds
     * @param roleId
     */
    @Transactional
    public void addRoleGroup(Set<String> groupIds, String roleId) {
        if (CollectionUtils.isEmpty(groupIds)) return;
        groupIds.forEach(groupId -> this.addRoleGroup(groupId, roleId));
    }

    /**
     * 删除关系
     * @param roleId
     * @param groupId
     */
    public void delBy(String roleId, String groupId) {
        if (StringUtils.isAnyBlank(roleId, groupId)) return;
        SysRoleGroup entity = new SysRoleGroup(roleId, groupId);
        this.getRepo().remove(entity);
    }

    /**
     * 删除关系
     * @param roleId
     */
    public void delByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) return;
        this.getRepo().remove(new SysRoleGroup(roleId, null));
    }

    /**
     * 删除关系
     * @param groupId
     */
    public void delByGroupId(String groupId) {
        if (StringUtils.isBlank(groupId)) return;
        this.getRepo().remove(new SysRoleGroup(null, groupId));
    }

}
