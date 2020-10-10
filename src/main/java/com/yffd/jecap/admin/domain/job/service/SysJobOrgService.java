package com.yffd.jecap.admin.domain.job.service;

import com.yffd.jecap.admin.domain.job.entity.SysJobOrg;
import com.yffd.jecap.admin.domain.job.repo.ISysJobRepo;
import com.yffd.jecap.admin.infrastructure.dao.job.ISysJobOrgDao;
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
public class SysJobOrgService {
    @Autowired private ISysJobRepo jobRepo;

    private ISysJobOrgDao getDao() {
        return this.jobRepo.getJobOrgDao();
    }

    /**
     * 查找关系
     * @param orgId
     * @return
     */
    public Set<String> queryJobIds(String orgId) {
        if (StringUtils.isBlank(orgId)) return Collections.emptySet();
        List<SysJobOrg> list = this.getDao().queryList(new SysJobOrg(null, orgId));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getJobId()));
        return ids;
    }

    /**
     * 查找关系
     * @param jobId
     * @return
     */
    public Set<String> queryOrgIds(String jobId) {
        if (StringUtils.isBlank(jobId)) return Collections.emptySet();
        List<SysJobOrg> list = this.getDao().queryList(new SysJobOrg(jobId, null));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getOrgId()));
        return ids;
    }

    /**
     * 添加或解除关系
     * @param jobId
     * @param addOrgIds
     * @param delOrgIds
     */
    @Transactional
    public void addAndDel(String jobId, Set<String> addOrgIds, Set<String> delOrgIds) {
        if (StringUtils.isBlank(jobId)) return;
        if (CollectionUtils.isNotEmpty(delOrgIds)) delOrgIds.forEach(org -> this.delBy(jobId, org));
        if (CollectionUtils.isNotEmpty(addOrgIds)) addOrgIds.forEach(org -> this.addJobOrg(jobId, org));
    }

    /**
     * 添加关系
     * @param jobId
     * @param orgId
     */
    public void addJobOrg(String jobId, String orgId) {
        if (StringUtils.isAnyBlank(jobId, orgId)) return;
        SysJobOrg entity = new SysJobOrg(jobId, orgId);
        if (null != this.getDao().queryOne(entity)) return;//已分配
        this.getDao().addBy(entity);
    }

    /**
     * 添加关系
     * @param jobId
     * @param orgIds
     */
    @Transactional
    public void addJobOrg(String jobId, Set<String> orgIds) {
        if (CollectionUtils.isEmpty(orgIds)) return;
        orgIds.forEach(orgId -> this.addJobOrg(jobId, orgId));
    }

    /**
     * 添加关系
     * @param jobIds
     * @param orgId
     */
    @Transactional
    public void addJobOrg(Set<String> jobIds, String orgId) {
        if (CollectionUtils.isEmpty(jobIds)) return;
        jobIds.forEach(groupId -> this.addJobOrg(groupId, orgId));
    }

    /**
     * 删除关系
     * @param jobId
     * @param orgId
     */
    public void delBy(String jobId, String orgId) {
        if (StringUtils.isAnyBlank(jobId, orgId)) return;
        this.getDao().removeBy(new SysJobOrg(jobId, orgId));
    }

    /**
     * 删除关系
     * @param jobId
     */
    public void delByJobId(String jobId) {
        if (StringUtils.isBlank(jobId)) return;
        this.getDao().removeBy(new SysJobOrg(jobId, null));
    }

    /**
     * 删除关系
     * @param orgId
     */
    public void delByOrgId(String orgId) {
        if (StringUtils.isBlank(orgId)) return;
        this.getDao().removeBy(new SysJobOrg(null, orgId));
    }

}
