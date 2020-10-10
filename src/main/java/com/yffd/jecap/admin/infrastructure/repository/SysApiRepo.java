package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.api.entity.SysApi;
import com.yffd.jecap.admin.domain.api.entity.SysApiPmsn;
import com.yffd.jecap.admin.domain.api.repo.ISysApiRepo;
import com.yffd.jecap.admin.infrastructure.dao.api.ISysApiDao;
import com.yffd.jecap.admin.infrastructure.dao.api.ISysApiPmsnDao;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysApiRepo implements ISysApiRepo {
    @Autowired private ISysApiDao apiDao;
    @Autowired private ISysApiPmsnDao apiPmsnDao;

    @Override
    public IBaseDao<SysApi> getApiDao() {
        return apiDao;
    }

    @Override
    public IBaseDao<SysApiPmsn> getApiPmsDao() {
        return apiPmsnDao;
    }

}
