package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.area.entity.SysArea;
import com.yffd.jecap.admin.domain.area.entity.SysAreaAddress;
import com.yffd.jecap.admin.domain.area.repo.ISysAreaRepo;
import com.yffd.jecap.admin.infrastructure.dao.area.ISysAreaAddressDao;
import com.yffd.jecap.admin.infrastructure.dao.area.ISysAreaDao;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysAreaRepo implements ISysAreaRepo {
    @Autowired private ISysAreaDao areaDao;
    @Autowired private ISysAreaAddressDao areaAddressDao;

    @Override
    public IBaseDao<SysArea> getAreaDao() {
        return areaDao;
    }

    @Override
    public IBaseDao<SysAreaAddress> getAreaAddressDao() {
        return areaAddressDao;
    }

    @Override
    public List<SysArea> queryByPath(String path) {
        return null;
    }
}
