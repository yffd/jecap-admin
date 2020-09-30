package com.yffd.jecap.admin.domain.menu.repo;

import com.yffd.jecap.admin.domain.menu.entity.SysMenu;
import com.yffd.jecap.admin.domain.menu.entity.SysMenuPmsn;
import com.yffd.jecap.common.base.dao.IBaseDao;

import java.util.List;

public interface ISysMenuRepo {

    IBaseDao<SysMenu> getMenuDao();
    IBaseDao<SysMenuPmsn> getMenuPmsnDao();

    List<SysMenu> findByPath(String path);
}
