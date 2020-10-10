package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.org.repo.ISysOrgRepo;
import com.yffd.jecap.admin.infrastructure.dao.org.ISysOrgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysOrgRepo implements ISysOrgRepo {
    @Autowired private ISysOrgDao orgDao;

    @Override
    public ISysOrgDao getOrgDao() {
        return orgDao;
    }
}
