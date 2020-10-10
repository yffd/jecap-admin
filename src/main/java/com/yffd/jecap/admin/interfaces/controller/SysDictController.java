package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.admin.domain.dict.entity.SysDict;
import com.yffd.jecap.admin.domain.dict.service.SysDictService;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统-字典】模块")
@RestController
@RequestMapping("/sys/dict")
public class SysDictController extends BaseController {
    @Autowired private SysDictService dictService;

    @ApiOperation(value = "树列表", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listTree")
    public RtnResult listTree(@RequestBody SysDict params) {
        return RtnResult.OK(this.dictService.queryTree());
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String dictId) {
        if (StringUtils.isBlank(dictId)) return RtnResult.FAIL("【字典ID】不能为空");
        return RtnResult.OK(this.dictService.queryById(dictId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody SysDict params) {
        this.dictService.add(params);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody SysDict params) {
        if (StringUtils.isBlank(params.getId())) return RtnResult.FAIL("【字典ID】不能为空");
        this.dictService.updateById(params);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String dictId) {
        if (StringUtils.isBlank(dictId)) return RtnResult.FAIL("【字典ID】不能为空");
        this.dictService.deleteById(dictId);
        return RtnResult.OK();
    }

}
