package com.yffd.jecap.admin.domain.role.service;

import com.yffd.jecap.admin.domain.role.entity.SysRolePmsn;
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
public class SysRolePmsnService {
    @Autowired private ISysRoleRepo roleRepo;

    private IBaseDao<SysRolePmsn> getDao() {
        return roleRepo.getRolePmsnDao();
    }

    /**
     * 查找关系
     * @param pmsnId
     * @return
     */
    public Set<String> queryRoleIds(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return Collections.emptySet();
        List<SysRolePmsn> list = this.getDao().queryList(new SysRolePmsn(null, pmsnId));
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
    public Set<String> queryPmsnIds(String roleId) {
        if (StringUtils.isBlank(roleId)) return Collections.emptySet();
        List<SysRolePmsn> list = this.getDao().queryList(new SysRolePmsn(roleId, null));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getPmsnId()));
        return ids;
    }

    /**
     * 添加或解除关系
     * @param roleId
     * @param addPmsnIds
     * @param delPmsnIds
     */
    @Transactional
    public void addAndDel(String roleId, Set<String> addPmsnIds, Set<String> delPmsnIds) {
        if (StringUtils.isBlank(roleId)) return;
        if (CollectionUtils.isNotEmpty(delPmsnIds)) delPmsnIds.forEach(tmp -> this.delBy(roleId, tmp));
        if (CollectionUtils.isNotEmpty(addPmsnIds)) addPmsnIds.forEach(tmp -> this.addRolePmsn(roleId, tmp));
    }

    /**
     * 添加关系
     * @param roleId
     * @param pmsnId
     */
    public void addRolePmsn(String roleId, String pmsnId) {
        if (StringUtils.isAnyBlank(roleId, pmsnId)) return;
        SysRolePmsn entity = new SysRolePmsn(roleId, pmsnId);
        if (null != this.getDao().queryOne(entity)) return;//已分配
        this.getDao().addBy(entity);
    }

    /**
     * 添加关系
     * @param roleId
     * @param pmsnIds
     */
    @Transactional
    public void addRolePmsn(String roleId, Set<String> pmsnIds) {
        if (CollectionUtils.isEmpty(pmsnIds)) return;
        pmsnIds.forEach(tmp -> this.addRolePmsn(roleId, tmp));
    }

    /**
     * 添加关系
     * @param roleIds
     * @param pmsnId
     */
    @Transactional
    public void addRolePmsn(Set<String> roleIds, String pmsnId) {
        if (CollectionUtils.isEmpty(roleIds)) return;
        roleIds.forEach(tmp -> this.addRolePmsn(tmp, pmsnId));
    }

    /**
     * 删除关系
     * @param roleId
     * @param pmsnId
     */
    public void delBy(String roleId, String pmsnId) {
        if (StringUtils.isAnyBlank(roleId, pmsnId)) return;
        this.getDao().removeBy(new SysRolePmsn(roleId, pmsnId));
    }

    /**
     * 删除关系
     * @param roleId
     * @param pmsnIds
     */
    public void delBy(String roleId, Set<String> pmsnIds) {
        if (StringUtils.isBlank(roleId) || CollectionUtils.isEmpty(pmsnIds)) return;
        this.roleRepo.removePmsn(roleId, pmsnIds);
    }

    /**
     * 删除关系
     * @param roleId
     */
    public void delByRoleId(String roleId) {
        if (StringUtils.isBlank(roleId)) return;
        this.getDao().removeBy(new SysRolePmsn(roleId, null));
    }

    /**
     * 删除关系
     * @param pmsn
     */
    public void delByPmsnId(String pmsn) {
        if (StringUtils.isBlank(pmsn)) return;
        this.getDao().removeBy(new SysRolePmsn(null, pmsn));
    }

}
