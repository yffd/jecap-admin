package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.admin.domain.org.entity.SysOrg;
import com.yffd.jecap.admin.domain.org.service.SysOrgService;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统-组织机构】模块")
@RestController
@RequestMapping("/sys/org")
public class SysOrgController extends BaseController {
    @Autowired private SysOrgService orgService;

    @ApiOperation(value = "分页", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listTree")
    public RtnResult listTree() {
        return RtnResult.OK(this.orgService.queryTree());
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String orgId) {
        if (StringUtils.isBlank(orgId)) return RtnResult.FAIL("【组织ID】不能为空");
        return RtnResult.OK(this.orgService.queryById(orgId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody SysOrg model) {
        this.orgService.add(model);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody SysOrg model) {
        if (StringUtils.isBlank(model.getId())) return RtnResult.FAIL("【组织ID】不能为空");
        this.orgService.updateById(model);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String roleId) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【组织ID】不能为空");
        this.orgService.deleteById(roleId);
        return RtnResult.OK();
    }

}
