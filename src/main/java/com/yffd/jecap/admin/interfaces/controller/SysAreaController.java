package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.admin.domain.area.entity.SysArea;
import com.yffd.jecap.admin.domain.area.service.SysAreaService;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统-区域】模块")
@RestController
@RequestMapping("/sys/area")
public class SysAreaController extends BaseController {
    @Autowired private SysAreaService areaService;

    @ApiOperation(value = "树列表", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listTree")
    public RtnResult listTree(@RequestBody SysArea params) {
        return RtnResult.OK(this.areaService.queryTree());
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String roleId) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【区域ID】不能为空");
        return RtnResult.OK(this.areaService.queryById(roleId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody SysArea params) {
        this.areaService.add(params);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody SysArea params) {
        if (StringUtils.isBlank(params.getId())) return RtnResult.FAIL("【区域ID】不能为空");
        this.areaService.updateById(params);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String roleId) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【区域ID】不能为空");
        this.areaService.deleteById(roleId);
        return RtnResult.OK();
    }

}
