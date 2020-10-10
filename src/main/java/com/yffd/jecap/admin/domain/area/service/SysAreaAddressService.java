package com.yffd.jecap.admin.domain.area.service;

import com.yffd.jecap.admin.domain.area.entity.SysAreaAddress;
import com.yffd.jecap.admin.domain.area.repo.ISysAreaRepo;
import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SysAreaAddressService {
    @Autowired private ISysAreaRepo areaRepo;

    private IBaseDao<SysAreaAddress> getDao() {
        return this.areaRepo.getAreaAddressDao();
    }

    public void add(SysAreaAddress address) {
        if (null == address || StringUtils.isAnyBlank(address.getAreaId(), address.getAddress()))
            throw SysException.cast("区域ID | 详细地址 不能为空").prompt();
        this.getDao().addBy(address);
    }

    public void updateById(SysAreaAddress address) {
        if (null == address || StringUtils.isBlank(address.getId())) return;
        this.getDao().modifyById(address);
    }

    public void delById(String addressId) {
        if (StringUtils.isBlank(addressId)) return;
        this.getDao().removeById(addressId);
    }

    public void delByAreaId(String areaId) {
        if (StringUtils.isBlank(areaId)) return;
        SysAreaAddress address = new SysAreaAddress();
        address.setAreaId(areaId);
        this.getDao().removeBy(address);
    }

    public SysAreaAddress queryById(String addressId) {
        if (StringUtils.isBlank(addressId)) return null;
        return this.getDao().queryById(addressId);
    }

    public List<SysAreaAddress> queryByAreaId(String areaId) {
        if (StringUtils.isBlank(areaId)) return Collections.emptyList();
        SysAreaAddress address = new SysAreaAddress();
        address.setAreaId(areaId);
        return this.getDao().queryList(address);
    }

}
