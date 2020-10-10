package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.admin.domain.api.entity.SysApi;
import com.yffd.jecap.admin.domain.api.service.SysApiService;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统-API接口】模块")
@RestController
@RequestMapping("/sys/api")
public class SysApiController extends BaseController {
    @Autowired private SysApiService apiService;

    @ApiOperation(value = "分页", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listPage")
    public RtnResult listPage(@RequestBody SysApi model) {
        return RtnResult.OK(this.apiService.queryPage(model, DEF_PAGE_NUM, DEF_PAGE_SIZE));
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String apiId) {
        if (StringUtils.isBlank(apiId)) return RtnResult.FAIL("【API ID】不能为空");
        return RtnResult.OK(this.apiService.queryById(apiId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody SysApi model) {
        this.apiService.add(model);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody SysApi model) {
        if (StringUtils.isBlank(model.getId())) return RtnResult.FAIL("【API ID】不能为空");
        this.apiService.updateById(model);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String apiId) {
        if (StringUtils.isBlank(apiId)) return RtnResult.FAIL("【API ID】不能为空");
        this.apiService.deleteById(apiId);
        return RtnResult.OK();
    }

}
