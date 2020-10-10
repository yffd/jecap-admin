package com.yffd.jecap.admin.domain.api.service;

import com.yffd.jecap.admin.domain.api.entity.SysApiPmsn;
import com.yffd.jecap.admin.domain.api.repo.ISysApiRepo;
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
public class SysApiPmsnService {
    @Autowired private ISysApiRepo apiRepo;

    private IBaseDao<SysApiPmsn> getDao() {
        return this.apiRepo.getApiPmsDao();
    }

    /**
     * 添加关系
     * @param apiId
     * @param pmsnId
     */
    public void addRlt(String apiId, String pmsnId) {
        if (StringUtils.isAnyBlank(apiId, pmsnId)) return;
        SysApiPmsn entity = new SysApiPmsn(apiId, pmsnId);
        if (null != this.getDao().queryOne(entity)) return;//已分配
        this.getDao().addBy(entity);
    }

    /**
     * 添加关系
     * @param apiId
     * @param pmsnIds
     */
    @Transactional
    public void addRlt(String apiId, Set<String> pmsnIds) {
        if (CollectionUtils.isEmpty(pmsnIds)) return;
        pmsnIds.forEach(tmp -> this.addRlt(apiId, tmp));
    }

    /**
     * 添加关系
     * @param apiIds
     * @param pmsnId
     */
    @Transactional
    public void addRlt(Set<String> apiIds, String pmsnId) {
        if (CollectionUtils.isEmpty(apiIds)) return;
        apiIds.forEach(tmp -> this.addRlt(tmp, pmsnId));
    }

    /**
     * 更新关系
     * @param apiId
     * @param addPmsnIds
     * @param delPmsnIds
     */
    @Transactional
    public void updateRlt(String apiId, Set<String> addPmsnIds, Set<String> delPmsnIds) {
        if (StringUtils.isBlank(apiId)) return;
        if (CollectionUtils.isNotEmpty(delPmsnIds)) delPmsnIds.forEach(tmp -> this.deleteBy(apiId, tmp));
        if (CollectionUtils.isNotEmpty(addPmsnIds)) addPmsnIds.forEach(tmp -> this.addRlt(apiId, tmp));
    }

    /**
     * 删除关系
     * @param apiId
     * @param pmsnId
     */
    public void deleteBy(String apiId, String pmsnId) {
        if (StringUtils.isAnyBlank(apiId, pmsnId)) return;
        this.getDao().removeBy(new SysApiPmsn(apiId, pmsnId));
    }

    /**
     * 删除关系
     * @param apiId
     */
    public void deleteByApiId(String apiId) {
        if (StringUtils.isBlank(apiId)) return;
        this.getDao().removeBy(new SysApiPmsn(apiId, null));
    }

    /**
     * 删除关系
     * @param pmsnId
     */
    public void deleteByPmsnId(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return;
        this.getDao().removeBy(new SysApiPmsn(null, pmsnId));
    }

    /**
     * 查找关系
     * @param pmsnId
     * @return
     */
    public Set<String> queryApiIds(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return Collections.emptySet();
        List<SysApiPmsn> list = this.getDao().queryList(new SysApiPmsn(null, pmsnId));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getApiId()));
        return ids;
    }

    /**
     * 查找关系
     * @param apiId
     * @return
     */
    public Set<String> queryPmsnIds(String apiId) {
        if (StringUtils.isBlank(apiId)) return Collections.emptySet();
        List<SysApiPmsn> list = this.getDao().queryList(new SysApiPmsn(apiId, null));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getPmsnId()));
        return ids;
    }

    /**
     * 查找关系
     * @param apiId
     * @return
     */
    public SysApiPmsn queryByApiId(String apiId) {
        if (StringUtils.isBlank(apiId)) return null;
        return this.getDao().queryOne(new SysApiPmsn(apiId, null));
    }



}
