package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.admin.domain.pmsn.entity.SysPmsn;
import com.yffd.jecap.admin.domain.pmsn.service.SysPmsnService;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统-权限】模块")
@RestController
@RequestMapping("/sys/pmsn")
public class SysPmsnController extends BaseController {
    @Autowired private SysPmsnService pmsnService;

    @ApiOperation(value = "分页", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listPage")
    public RtnResult listPage(@RequestBody SysPmsn model) {
        return RtnResult.OK(this.pmsnService.queryPage(model, DEF_PAGE_NUM, DEF_PAGE_SIZE));
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return RtnResult.FAIL("【权限ID】不能为空");
        return RtnResult.OK(this.pmsnService.queryById(pmsnId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody SysPmsn model) {
        this.pmsnService.add(model);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody SysPmsn model) {
        if (StringUtils.isBlank(model.getId())) return RtnResult.FAIL("【权限ID】不能为空");
        this.pmsnService.updateById(model);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return RtnResult.FAIL("【权限ID】不能为空");
        this.pmsnService.deleteById(pmsnId);
        return RtnResult.OK();
    }

}
