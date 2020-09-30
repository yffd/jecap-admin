package com.yffd.jecap.admin.domain.menu.service;

import com.yffd.jecap.admin.domain.menu.entity.SysMenuPmsn;
import com.yffd.jecap.admin.domain.menu.repo.ISysMenuRepo;
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
public class SysMenuPmsnService {
    @Autowired private ISysMenuRepo menuRepo;

    private IBaseDao<SysMenuPmsn> getDao() {
        return this.menuRepo.getMenuPmsnDao();
    }

    /**
     * 查找关系
     * @param pmsnId
     * @return
     */
    public Set<String> findMenuIds(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return Collections.emptySet();
        List<SysMenuPmsn> list = this.getDao().findList(new SysMenuPmsn(null, pmsnId));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getMenuId()));
        return ids;
    }

    /**
     * 查找关系
     * @param menuId
     * @return
     */
    public Set<String> findPmsnIds(String menuId) {
        if (StringUtils.isBlank(menuId)) return Collections.emptySet();
        List<SysMenuPmsn> list = this.getDao().findList(new SysMenuPmsn(menuId, null));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getPmsnId()));
        return ids;
    }

    /**
     * 添加或解除关系
     * @param menuId
     * @param addPmsnIds
     * @param delPmsnIds
     */
    @Transactional
    public void addAndDel(String menuId, Set<String> addPmsnIds, Set<String> delPmsnIds) {
        if (StringUtils.isBlank(menuId)) return;
        if (CollectionUtils.isNotEmpty(delPmsnIds)) delPmsnIds.forEach(tmp -> this.delBy(menuId, tmp));
        if (CollectionUtils.isNotEmpty(addPmsnIds)) addPmsnIds.forEach(tmp -> this.addRlt(menuId, tmp));
    }

    /**
     * 添加关系
     * @param menuId
     * @param pmsnId
     */
    public void addRlt(String menuId, String pmsnId) {
        if (StringUtils.isAnyBlank(menuId, pmsnId)) return;
        SysMenuPmsn entity = new SysMenuPmsn(menuId, pmsnId);
        if (null != this.getDao().findOne(entity)) return;//已分配
        this.getDao().addBy(entity);
    }

    /**
     * 添加关系
     * @param menuId
     * @param pmsnIds
     */
    @Transactional
    public void addRlt(String menuId, Set<String> pmsnIds) {
        if (CollectionUtils.isEmpty(pmsnIds)) return;
        pmsnIds.forEach(tmp -> this.addRlt(menuId, tmp));
    }

    /**
     * 添加关系
     * @param menuIds
     * @param pmsnId
     */
    @Transactional
    public void addRlt(Set<String> menuIds, String pmsnId) {
        if (CollectionUtils.isEmpty(menuIds)) return;
        menuIds.forEach(tmp -> this.addRlt(tmp, pmsnId));
    }

    /**
     * 删除关系
     * @param menuId
     * @param pmsnId
     */
    public void delBy(String menuId, String pmsnId) {
        if (StringUtils.isAnyBlank(menuId, pmsnId)) return;
        this.getDao().removeBy(new SysMenuPmsn(menuId, pmsnId));
    }

    /**
     * 删除关系
     * @param menuId
     */
    public void delByMenuId(String menuId) {
        if (StringUtils.isBlank(menuId)) return;
        this.getDao().removeBy(new SysMenuPmsn(menuId, null));
    }

    /**
     * 删除关系
     * @param pmsnId
     */
    public void delByPmsnId(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return;
        this.getDao().removeBy(new SysMenuPmsn(null, pmsnId));
    }

}
