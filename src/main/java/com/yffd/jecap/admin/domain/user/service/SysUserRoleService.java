package com.yffd.jecap.admin.domain.user.service;

import com.yffd.jecap.admin.domain.user.entity.SysUserRole;
import com.yffd.jecap.admin.domain.user.repo.ISysUserRepo;
import com.yffd.jecap.common.base.repository.IBaseRepository;
import com.yffd.jecap.common.base.service.AbstractBaseService;
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
public class SysUserRoleService extends AbstractBaseService<SysUserRole> {
    @Autowired private ISysUserRepo sysUserRepo;

    @Override
    protected IBaseRepository getRepo() {
        return sysUserRepo;
    }

    /**
     * 查找关系
     * @param userId
     * @return
     */
    public Set<String> findRoleIds(String userId) {
        if (StringUtils.isBlank(userId)) return Collections.emptySet();
        SysUserRole entity = new SysUserRole(userId, null);
        List<SysUserRole> list = this.getRepo().getList(entity);
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
    public Set<String> findUserIds(String roleId) {
        if (StringUtils.isBlank(roleId)) return Collections.emptySet();
        SysUserRole entity = new SysUserRole(null, roleId);
        List<SysUserRole> list = this.getRepo().getList(entity);
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getUserId()));
        return ids;
    }

    /**
     * 添加或解除关系
     * @param userId
     * @param addRoleIds
     * @param delRoleIds
     */
    @Transactional
    public void addAndDel(String userId, Set<String> addRoleIds, Set<String> delRoleIds) {
        if (StringUtils.isBlank(userId)) return;
        if (CollectionUtils.isNotEmpty(delRoleIds)) delRoleIds.forEach(groupId -> this.delBy(userId, groupId));
        if (CollectionUtils.isNotEmpty(addRoleIds)) addRoleIds.forEach(groupId -> this.addUserRole(userId, groupId));
    }

    /**
     * 添加关系
     * @param userId
     * @param roleId
     */
    public void addUserRole(String userId, String roleId) {
        if (StringUtils.isAnyBlank(userId, roleId)) return;
        SysUserRole entity = new SysUserRole(userId, roleId);
        if (null != this.getRepo().get(entity)) return;//已分配
        this.getRepo().add(entity);
    }

    /**
     * 添加关系
     * @param userId
     * @param roleIds
     */
    @Transactional
    public void addUserRole(String userId, Set<String> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) return;
        roleIds.forEach(roleId -> this.addUserRole(userId, roleId));
    }

    /**
     * 添加关系
     * @param userIds
     * @param roleId
     */
    @Transactional
    public void addUserRole(Set<String> userIds, String roleId) {
        if (CollectionUtils.isEmpty(userIds)) return;
        userIds.forEach(groupId -> this.addUserRole(groupId, roleId));
    }

    /**
     * 删除关系
     * @param userId
     * @param roleId
     */
    public void delBy(String userId, String roleId) {
        if (StringUtils.isAnyBlank(userId, roleId)) return;
        this.getRepo().remove(new SysUserRole(userId, roleId));
    }

    /**
     * 删除关系
     * @param roleId
     */
    public void delByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) return;
        this.getRepo().remove(new SysUserRole(null, roleId));
    }

    /**
     * 删除关系
     * @param userId
     */
    public void delByUserId(String userId) {
        if (StringUtils.isBlank(userId)) return;
        this.getRepo().remove(new SysUserRole(userId, null));
    }

}
