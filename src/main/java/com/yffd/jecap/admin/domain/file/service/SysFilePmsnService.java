package com.yffd.jecap.admin.domain.file.service;

import com.yffd.jecap.admin.domain.file.entity.SysFilePmsn;
import com.yffd.jecap.admin.domain.file.repo.ISysFileRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
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
public class SysFilePmsnService {
    @Autowired private ISysFileRepo fileRepo;

    private IBaseDao<SysFilePmsn> getDao() {
        return this.fileRepo.getFilePmsnDao();
    }

    /**
     * 添加关系
     * @param fileId
     * @param pmsnId
     */
    public void addRlt(String fileId, String pmsnId) {
        if (StringUtils.isAnyBlank(fileId, pmsnId)) return;
        SysFilePmsn entity = new SysFilePmsn(fileId, pmsnId);
        if (null != this.getDao().queryOne(entity)) return;//已分配
        this.getDao().addBy(entity);
    }

    /**
     * 添加关系
     * @param fileId
     * @param pmsnIds
     */
    @Transactional
    public void addRlt(String fileId, Set<String> pmsnIds) {
        if (CollectionUtils.isEmpty(pmsnIds)) return;
        pmsnIds.forEach(tmp -> this.addRlt(fileId, tmp));
    }

    /**
     * 添加关系
     * @param fileIds
     * @param pmsnId
     */
    @Transactional
    public void addRlt(Set<String> fileIds, String pmsnId) {
        if (CollectionUtils.isEmpty(fileIds)) return;
        fileIds.forEach(tmp -> this.addRlt(tmp, pmsnId));
    }

    /**
     * 更新关系
     * @param fileId
     * @param addPmsnIds
     * @param delPmsnIds
     */
    @Transactional
    public void updateRlt(String fileId, Set<String> addPmsnIds, Set<String> delPmsnIds) {
        if (StringUtils.isBlank(fileId)) return;
        if (CollectionUtils.isNotEmpty(delPmsnIds)) delPmsnIds.forEach(tmp -> this.deleteBy(fileId, tmp));
        if (CollectionUtils.isNotEmpty(addPmsnIds)) addPmsnIds.forEach(tmp -> this.addRlt(fileId, tmp));
    }

    /**
     * 删除关系
     * @param fileId
     * @param pmsnId
     */
    public void deleteBy(String fileId, String pmsnId) {
        if (StringUtils.isAnyBlank(fileId, pmsnId)) return;
        this.getDao().removeBy(new SysFilePmsn(fileId, pmsnId));
    }

    /**
     * 删除关系
     * @param fileId
     */
    public void deleteByFileId(String fileId) {
        if (StringUtils.isBlank(fileId)) return;
        this.getDao().removeBy(new SysFilePmsn(fileId, null));
    }

    /**
     * 删除关系
     * @param pmsnId
     */
    public void deleteByPmsnId(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return;
        this.getDao().removeBy(new SysFilePmsn(null, pmsnId));
    }

    /**
     * 查找关系
     * @param pmsnId
     * @return
     */
    public Set<String> queryFileIds(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return Collections.emptySet();
        List<SysFilePmsn> list = this.getDao().queryList(new SysFilePmsn(null, pmsnId));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getFileId()));
        return ids;
    }

    /**
     * 查找关系
     * @param fileId
     * @return
     */
    public Set<String> queryPmsnIds(String fileId) {
        if (StringUtils.isBlank(fileId)) return Collections.emptySet();
        List<SysFilePmsn> list = this.getDao().queryList(new SysFilePmsn(fileId, null));
        if (CollectionUtils.isEmpty(list)) return Collections.emptySet();
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getPmsnId()));
        return ids;
    }

    /**
     * 插叙外键
     * @param fileId
     * @return
     */
    public SysFilePmsn queryByFileId(String fileId) {
        if (null == fileId) return null;
        return this.getDao().queryOne(new SysFilePmsn(fileId, null));
    }
}
