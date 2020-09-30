package com.yffd.jecap.admin.domain.pmsn.repo;

import com.yffd.jecap.admin.domain.pmsn.entity.SysPmsn;
import com.yffd.jecap.common.base.dao.IBaseDao;

public interface ISysPmsnRepo {

    IBaseDao<SysPmsn> getPmsnDao();

}
