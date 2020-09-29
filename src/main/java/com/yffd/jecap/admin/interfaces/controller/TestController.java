package com.yffd.jecap.admin.interfaces.controller;

import com.yffd.jecap.common.base.web.controller.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController extends BaseController {


    @ApiOperation(value = "登录sss")
    @ApiImplicitParam(name = "params" , paramType = "body", example = "{'loginName':'string', 'loginPwd':'string'}")
    @PostMapping("/")
    public void login() {
        System.out.println("ssssssssssssssssss");
        System.out.println("ssssssssssssssssss");
        System.out.println("ssssssssssssssssss");
    }


}
