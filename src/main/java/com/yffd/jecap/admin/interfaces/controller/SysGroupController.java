package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.admin.domain.group.entity.SysGroup;
import com.yffd.jecap.admin.domain.group.service.SysGroupService;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统-组】模块")
@RestController
@RequestMapping("/sys/group")
public class SysGroupController extends BaseController {
    @Autowired private SysGroupService groupService;

    @ApiOperation(value = "分页", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listPage")
    public RtnResult listPage(@RequestBody SysGroup model) {
        return RtnResult.OK(this.groupService.queryPage(model, DEF_PAGE_NUM, DEF_PAGE_SIZE));
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String roleId) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【组ID】不能为空");
        return RtnResult.OK(this.groupService.queryById(roleId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody SysGroup model) {
        this.groupService.add(model);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody SysGroup model) {
        if (StringUtils.isBlank(model.getId())) return RtnResult.FAIL("【组ID】不能为空");
        this.groupService.updateById(model);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String groupId) {
        if (StringUtils.isBlank(groupId)) return RtnResult.FAIL("【组ID】不能为空");
        this.groupService.deleteById(groupId);
        return RtnResult.OK();
    }

}
