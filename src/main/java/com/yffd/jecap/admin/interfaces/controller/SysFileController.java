package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.admin.application.dto.file.FileSaveDto;
import com.yffd.jecap.admin.application.service.SysFileAppService;
import com.yffd.jecap.admin.domain.file.entity.SysFile;
import com.yffd.jecap.admin.domain.file.service.SysFileService;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.common.base.result.RtnResult;
import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【系统-文件】模块")
@RestController
@RequestMapping("/sys/file")
public class SysFileController extends BaseController {
    @Autowired private SysFileService fileService;
    @Autowired private SysFileAppService fileAppService;

    @ApiOperation(value = "分页", consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/listPage")
    public RtnResult listPage(@RequestBody SysFile model) {
        return RtnResult.OK(this.fileService.queryPage(model, DEF_PAGE_NUM, DEF_PAGE_SIZE));
    }

    @ApiOperation(value = "详细")
    @GetMapping("/getDetail")
    public RtnResult getDetail(String fielId) {
        if (StringUtils.isBlank(fielId)) return RtnResult.FAIL("【文件ID】不能为空");
        return RtnResult.OK(this.fileService.queryById(fielId));
    }

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public RtnResult add(@RequestBody FileSaveDto model) {
        this.fileAppService.add(model);
        return RtnResult.OK();
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public RtnResult update(@RequestBody FileSaveDto model) {
        this.fileAppService.update(model);
        return RtnResult.OK();
    }

    @ApiOperation(value = "删除", consumes = "application/x-www-form-urlencoded")
    @PostMapping("/delete")
    public RtnResult delete(String fielId) {
        if (StringUtils.isBlank(fielId)) return RtnResult.FAIL("【文件ID】不能为空");
        this.fileService.deleteById(fielId);
        return RtnResult.OK();
    }

}
