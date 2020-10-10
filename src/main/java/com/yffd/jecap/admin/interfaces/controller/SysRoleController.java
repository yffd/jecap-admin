package com.yffd.jecap.admin.interfaces.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yffd.jecap.admin.application.dto.role.RoleSaveDto;
import com.yffd.jecap.admin.application.service.SysRoleAppService;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.admin.domain.role.service.SysRoleService;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@Api(tags = "【系统-角色】模块")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
    @Autowired private SysRoleService roleService;
    @Autowired private SysRoleAppService roleAppService;

    @ApiOperation(value = "分页", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listPage")
    public RtnResult listPage(@RequestBody SysRole model) {
        return RtnResult.OK(this.roleService.queryPage(model, DEF_PAGE_NUM, DEF_PAGE_SIZE));
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String roleId) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【角色ID】不能为空");
        return RtnResult.OK(this.roleService.queryById(roleId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody RoleSaveDto model) {
        this.roleAppService.add(model);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody RoleSaveDto model) {
        this.roleAppService.update(model);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String roleId) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【角色ID】不能为空");
        this.roleService.deleteById(roleId);
        return RtnResult.OK();
    }

    @ApiOperation("启用账号")
    @PostMapping("/enable")
    public RtnResult enable(String roleId) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【角色ID】不能为空");
        this.roleService.enable(roleId);
        return RtnResult.OK();
    }

    @ApiOperation("禁用账号")
    @PostMapping("/disable")
    public RtnResult disable(String roleId) {
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【角色ID】不能为空");
        this.roleService.disable(roleId);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除角色组")
    @ApiImplicitParam(name = "params" , paramType = "body", example = "{'userId':'string', 'groupIds':['id_1','id_2']}")
    @PostMapping("/deleteGroup")
    public RtnResult deleteGroup(@RequestBody JSONObject params) {
        String roleId = (String) params.get("roleId");
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【用户ID】不能为空");
        JSONArray array = params.getJSONArray("groupIds");
        String[] groupIds = null;
        if (null != array) groupIds = array.toArray(new String[array.size()]);
        return this.roleAppService.delRoleGroup(roleId, Arrays.stream(groupIds).collect(Collectors.toSet()));
    }

    @ApiOperation(value = "删除权限")
    @ApiImplicitParam(name = "params" , paramType = "body", example = "{'userId':'string', 'pmsnIds':['id_1','id_2']}")
    @PostMapping("/deletePmsn")
    public RtnResult deletePmsn(@RequestBody JSONObject params) {
        String roleId = (String) params.get("roleId");
        if (StringUtils.isBlank(roleId)) return RtnResult.FAIL("【用户ID】不能为空");
        JSONArray array = params.getJSONArray("pmsnIds");
        String[] pmsnIds = null;
        if (null != array) pmsnIds = array.toArray(new String[array.size()]);
        return this.roleAppService.delRolePmsn(roleId, Arrays.stream(pmsnIds).collect(Collectors.toSet()));
    }

}
