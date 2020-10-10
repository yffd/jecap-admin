package com.yffd.jecap.admin.domain.user.service;

import com.yffd.jecap.admin.domain.user.entity.SysUserGroup;
import com.yffd.jecap.admin.domain.user.repo.ISysUserRepo;
import com.yffd.jecap.admin.infrastructure.dao.user.ISysUserGroupDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SysUserGroupService {
    @Autowired private ISysUserRepo userRepo;

    private ISysUserGroupDao getDao() {
        return this.userRepo.getUserGroupDao();
    }

    /**
     * 查找关系
     * @param groupId
     * @return
     */
    public Set<String> queryUserIds(String groupId) {
        if (StringUtils.isBlank(groupId)) return Collections.emptySet();
        SysUserGroup entity = new SysUserGroup(null, groupId);
        List<SysUserGroup> list = this.getDao().queryList(entity);
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getUserId()));
        return ids;
    }

    /**
     * 查找关系
     * @param userId
     * @return
     */
    public Set<String> queryGroupIds(String userId) {
        if (StringUtils.isBlank(userId)) return Collections.emptySet();
        SysUserGroup entity = new SysUserGroup(userId, null);
        List<SysUserGroup> list = this.getDao().queryList(entity);
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getGroupId()));
        return ids;
    }

    /**
     * 添加或解除关系
     * @param userId
     * @param addGroupIds
     * @param delGroupIds
     */
    @Transactional
    public void addAndDel(String userId, Set<String> addGroupIds, Set<String> delGroupIds) {
        if (StringUtils.isBlank(userId)) return;
        if (CollectionUtils.isNotEmpty(delGroupIds)) delGroupIds.forEach(groupId -> this.delBy(userId, groupId));
        if (CollectionUtils.isNotEmpty(addGroupIds)) addGroupIds.forEach(groupId -> this.addUserGroup(userId, groupId));
    }

    /**
     * 添加关系
     * @param userId
     * @param groupId
     */
    public void addUserGroup(String userId, String groupId) {
        if (StringUtils.isAnyBlank(userId, groupId)) return;
        SysUserGroup entity = new SysUserGroup(userId, groupId);
        if (null != this.getDao().queryOne(entity)) return;//已分配
        this.getDao().addBy(entity);
    }

    /**
     * 添加关系
     * @param userId
     * @param groupIds
     */
    @Transactional
    public void addUserGroup(String userId, Set<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) return;
        groupIds.forEach(groupId -> this.addUserGroup(userId, groupId));
    }

    /**
     * 添加关系
     * @param groupIds
     * @param userId
     */
    @Transactional
    public void addUserGroup(Set<String> groupIds, String userId) {
        if (CollectionUtils.isEmpty(groupIds)) return;
        groupIds.forEach(groupId -> this.addUserGroup(groupId, userId));
    }

    /**
     * 删除关系
     * @param userId
     * @param groupId
     */
    public void delBy(String userId, String groupId) {
        if (StringUtils.isAnyBlank(userId, groupId)) return;
        SysUserGroup entity = new SysUserGroup(userId, groupId);
        this.getDao().removeBy(entity);
    }

    /**
     * 删除关系
     * @param userId
     * @param groupIds
     */
    public void delBy(String userId, Set<String> groupIds) {
        if (StringUtils.isBlank(userId) || CollectionUtils.isEmpty(groupIds)) return;
        this.userRepo.removeGroup(userId, groupIds);
    }

    /**
     * 删除关系
     * @param userId
     */
    public void delByUserId(String userId) {
        if (StringUtils.isBlank(userId)) return;
        this.getDao().removeBy(new SysUserGroup(userId, null));
    }

    /**
     * 删除关系
     * @param groupId
     */
    public void delByGroupId(String groupId) {
        if (StringUtils.isBlank(groupId)) return;
        this.getDao().removeBy(new SysUserGroup(null, groupId));
    }

}
