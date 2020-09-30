package com.yffd.jecap.admin.domain.api.repo;

import com.yffd.jecap.admin.domain.api.entity.SysApi;
import com.yffd.jecap.admin.domain.api.entity.SysApiPmsn;
import com.yffd.jecap.common.base.dao.IBaseDao;

public interface ISysApiRepo {

    IBaseDao<SysApi> getApiDao();
    IBaseDao<SysApiPmsn> getApiPmsDao();

}
