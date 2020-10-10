package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.admin.application.dto.job.JobSaveDto;
import com.yffd.jecap.admin.application.service.SysJobAppService;
import com.yffd.jecap.admin.domain.job.entity.SysJob;
import com.yffd.jecap.admin.domain.job.service.SysJobService;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统-岗位】模块")
@RestController
@RequestMapping("/sys/job")
public class SysJobController extends BaseController {
    @Autowired private SysJobService jobService;
    @Autowired private SysJobAppService jobAppService;

    @ApiOperation(value = "分页", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listPage")
    public RtnResult listPage(@RequestBody SysJob params) {
        return RtnResult.OK(this.jobService.queryPage(params, DEF_PAGE_NUM, DEF_PAGE_SIZE));
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String jobId) {
        if (StringUtils.isBlank(jobId)) return RtnResult.FAIL("【岗位ID】不能为空");
        return RtnResult.OK(this.jobService.queryById(jobId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody JobSaveDto params) {
        this.jobAppService.add(params);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @ApiImplicitParam(name = "params" , paramType = "body", example = "{'userId':'string', 'orgId':'string'}")
    @PostMapping("/update")
    public RtnResult update(@RequestBody JobSaveDto params) {
        this.jobAppService.update(params);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String jobId) {
        if (StringUtils.isBlank(jobId)) return RtnResult.FAIL("【岗位ID】不能为空");
        this.jobAppService.delete(jobId);
        return RtnResult.OK();
    }

}
