package com.yffd.jecap.admin.domain.user.service;

import com.alibaba.fastjson.JSON;
import com.yffd.jecap.admin.BaseTest;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.common.base.page.PageData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SysUserServiceTest extends BaseTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void addTest() {
        SysUser entity = new SysUser();
        entity.setAcntName("admin");
        entity.setAcntPwd("111111");
        entity.setUserName("admin");
        this.sysUserService.add(entity);
    }

    @Test
    public void updateByIdTest() {
        SysUser entity = new SysUser();
        entity.setId("1303982227998240769");
        entity.setUserName("李四-abc");
        this.sysUserService.updateById(entity);
    }

    @Test
    public void deleteByIdTest() {
        this.sysUserService.deleteById("1303982227998240769");
    }

    @Test
    public void getListTest() {
        SysUser entity = new SysUser();
        entity.setUserName("李四");
        PageData<SysUser> data = this.sysUserService.queryPage(entity, 1, 10);
        System.out.println(JSON.toJSONString(data));
    }
}
