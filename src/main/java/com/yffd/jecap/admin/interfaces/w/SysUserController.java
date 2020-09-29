package com.yffd.jecap.admin.interfaces.w;

import com.yffd.jecap.admin.application.service.SysUserAppService;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "系统-用户模块")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserAppService userService;

    @ApiOperation(value = "分页查询", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listPage")
    public RtnResult listPage(@RequestBody SysUser entity) {
        return this.userService.findPage(entity, DEF_PAGE_NUM, DEF_PAGE_SIZE);
    }

    @ApiOperation(value = "根据ID查询")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String userId) {
        if (StringUtils.isBlank(userId)) return RtnResult.FAIL("【用户ID】不能为空");
        return this.userService.findById(userId);
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody SysUser model) {
        this.userService.add(model);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody SysUser model) {
        if (StringUtils.isBlank(model.getId())) return RtnResult.FAIL("【用户ID】不能为空");
        return this.userService.updateById(model);
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
//    @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "body", dataTypeClass = String.class)
    @PostMapping("/delete")
    public RtnResult delete(String userId) {
        if (StringUtils.isBlank(userId)) return RtnResult.FAIL("【用户ID】不能为空");
        return this.userService.deleteById(userId);
    }

    @ApiOperation(value = "登录")
    @ApiImplicitParam(name = "params" , paramType = "body", example = "{'loginName':'string', 'loginPwd':'string'}")
    @PostMapping("/login")
    public RtnResult login(@RequestBody Map<String, String> params) {
        return this.userService.doLogin(params.get("loginName"), params.get("loginPwd"));
    }

    @ApiOperation(value = "根据token获取当前登录信息")
    @GetMapping("/getInfo")
    public RtnResult getInfo(@Param(value = "tokenId") String tokenId) {
        return this.userService.findLoginInfo(StringUtils.isNotBlank(tokenId) ? tokenId : this.getTokenId());
    }

    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public RtnResult logout(@Param(value = "tokenId") String tokenId) {
        return this.userService.doLogout(StringUtils.isNotBlank(tokenId) ? tokenId : this.getTokenId());
    }

}
