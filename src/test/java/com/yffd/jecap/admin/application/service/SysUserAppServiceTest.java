package com.yffd.jecap.admin.application.service;

import com.alibaba.fastjson.JSON;
import com.yffd.jecap.admin.BaseTest;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.common.base.result.RtnResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SysUserAppServiceTest extends BaseTest {
    @Autowired
    private SysUserAppService sysUserAppService;

    @Test
    public void addWithAccountTest() {
        SysUser entity = new SysUser();
        entity.setLoginName("admin");
        entity.setLoginPwd("111111");
        entity.setUserName("admin");
        RtnResult<String> result = this.sysUserAppService.addWithAccount(entity);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void getListTest() {
        SysUser entity = new SysUser();
        entity.setUserName("李四");
        RtnResult<SysUser> list = this.sysUserAppService.findPage(entity, 1, 10);
        System.out.println(JSON.toJSONString(list));
    }
}
