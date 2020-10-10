package com.yffd.jecap.admin.application.service;

import com.yffd.jecap.admin.application.dto.job.JobSaveDto;
import com.yffd.jecap.admin.domain.job.service.SysJobOrgService;
import com.yffd.jecap.admin.domain.job.service.SysJobService;
import com.yffd.jecap.common.base.result.RtnResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SysJobAppService {
    @Autowired private SysJobService jobService;
    @Autowired private SysJobOrgService jobOrgService;

    public RtnResult add(JobSaveDto dto) {
        if (null == dto || null == dto.getJob()) return RtnResult.FAIL_PARAM_ISNULL();
        this.jobService.add(dto.getJob());
        //所属组织机构
        if (StringUtils.isNotBlank(dto.getJob().getId()))
            this.jobOrgService.addJobOrg(dto.getJob().getId(), dto.getOrgId());
        return RtnResult.OK();
    }

    public RtnResult update(JobSaveDto dto) {
        if (null == dto || null == dto.getJob()) return RtnResult.FAIL_PARAM_ISNULL();
        String jobId = dto.getJob().getId();
        if (StringUtils.isBlank(jobId)) return RtnResult.FAIL("【岗位ID】不能为空");
        this.jobService.updateById(dto.getJob());
        //更新所属组织机构
        if (StringUtils.isNotEmpty(dto.getOrgId())) {
            this.jobOrgService.delByJobId(jobId);
            this.jobOrgService.addJobOrg(jobId, dto.getOrgId());
        }
        return RtnResult.OK();
    }

    public void delete(String jobId) {
        if (StringUtils.isBlank(jobId)) return;
        this.jobService.deleteById(jobId);
        this.jobOrgService.delByJobId(jobId);//删除关联关系
    }

}
