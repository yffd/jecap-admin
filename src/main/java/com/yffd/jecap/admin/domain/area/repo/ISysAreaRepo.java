package com.yffd.jecap.admin.domain.area.repo;

import com.yffd.jecap.admin.domain.area.entity.SysArea;
import com.yffd.jecap.admin.domain.area.entity.SysAreaAddress;
import com.yffd.jecap.common.base.dao.IBaseDao;

import java.util.List;

public interface ISysAreaRepo {

    IBaseDao<SysArea> getAreaDao();
    IBaseDao<SysAreaAddress> getAreaAddressDao();

    List<SysArea> findByPath(String path);
}
