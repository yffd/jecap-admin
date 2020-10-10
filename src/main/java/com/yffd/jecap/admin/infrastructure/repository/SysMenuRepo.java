package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.menu.entity.SysMenu;
import com.yffd.jecap.admin.domain.menu.entity.SysMenuPmsn;
import com.yffd.jecap.admin.domain.menu.repo.ISysMenuRepo;
import com.yffd.jecap.admin.infrastructure.dao.menu.ISysMenuDao;
import com.yffd.jecap.admin.infrastructure.dao.menu.ISysMenuPmsnDao;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysMenuRepo implements ISysMenuRepo {
    @Autowired private ISysMenuDao menuDao;
    @Autowired private ISysMenuPmsnDao menuPmsnDao;

    @Override
    public IBaseDao<SysMenu> getMenuDao() {
        return menuDao;
    }

    @Override
    public IBaseDao<SysMenuPmsn> getMenuPmsnDao() {
        return menuPmsnDao;
    }

    @Override
    public List<SysMenu> queryByPath(String path) {
        return null;
    }
}
