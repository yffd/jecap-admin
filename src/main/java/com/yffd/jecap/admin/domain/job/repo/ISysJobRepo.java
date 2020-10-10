package com.yffd.jecap.admin.domain.job.repo;

import com.yffd.jecap.admin.infrastructure.dao.job.ISysJobDao;
import com.yffd.jecap.admin.infrastructure.dao.job.ISysJobOrgDao;

public interface ISysJobRepo {

    ISysJobDao getJobDao();
    ISysJobOrgDao getJobOrgDao();

}
