package com.yffd.jecap.admin.domain.job.service;

import com.yffd.jecap.admin.domain.exception.AdminException;
import com.yffd.jecap.admin.domain.job.entity.SysJob;
import com.yffd.jecap.admin.domain.job.repo.ISysJobRepo;
import com.yffd.jecap.admin.infrastructure.dao.job.ISysJobDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysJobService {
    @Autowired private SysJobOrgService jobOrgService;

    @Autowired private ISysJobRepo jobRepo;

    private ISysJobDao getDao() {
        return this.jobRepo.getJobDao();
    }

    public void add(SysJob job) {
        if (null == job || StringUtils.isBlank(job.getJobName())) throw AdminException.cast("岗位名称不能为空").prompt();
        if (StringUtils.isNotBlank(job.getJobCode()) && this.existByJobCode(job.getJobCode())) {
            throw AdminException.cast(String.format("岗位编号【%s】已存在", job.getJobCode())).prompt();
        }
        this.getDao().addBy(job);
    }

    public void updateById(SysJob job) {
        if (null == job || StringUtils.isBlank(job.getId())) return;
        if (StringUtils.isNotBlank(job.getJobCode())) {
            SysJob entity = this.findByJobCode(job.getJobCode());
            if (null != entity && !entity.getId().equals(job.getId())) {
                throw AdminException.cast(String.format("岗位编号【%s】已存在", job.getJobCode())).prompt();
            }
        }
        this.getDao().modifyById(job);
    }

    @Transactional
    public void delById(String jobId) {
        if (StringUtils.isBlank(jobId)) return;
        this.getDao().deleteById(jobId);
        this.jobOrgService.delByJobId(jobId);//删除关联关系
    }

    public boolean existByJobCode(String jobCode) {
        return null != this.findByJobCode(jobCode);
    }

    public SysJob findByJobCode(String jobCode) {
        if (StringUtils.isBlank(jobCode)) return null;
        SysJob entity = new SysJob();
        entity.setJobCode(jobCode);
        return this.getDao().findOne(entity);
    }
}
