package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.group.repo.ISysGroupRepo;
import com.yffd.jecap.admin.infrastructure.dao.group.ISysGroupDao;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysGroupRepo implements ISysGroupRepo {
    @Autowired private ISysGroupDao groupDao;

    @Override
    public IBaseDao getGroupDao() {
        return groupDao;
    }
}
