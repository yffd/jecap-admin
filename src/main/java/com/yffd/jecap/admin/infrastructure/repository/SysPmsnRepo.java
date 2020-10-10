package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.pmsn.entity.SysPmsn;
import com.yffd.jecap.admin.domain.pmsn.repo.ISysPmsnRepo;
import com.yffd.jecap.admin.infrastructure.dao.pmsn.ISysPmsnDao;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysPmsnRepo implements ISysPmsnRepo {
    @Autowired private ISysPmsnDao pmsnDao;

    @Override
    public IBaseDao<SysPmsn> getPmsnDao() {
        return pmsnDao;
    }
}
