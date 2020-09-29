package com.yffd.jecap.admin.domain.org.repo;

import com.yffd.jecap.admin.domain.org.entity.SysOrg;
import com.yffd.jecap.common.base.repository.IBaseRepository;

import java.util.List;

public interface ISysOrgRepo extends IBaseRepository<SysOrg> {
    List<SysOrg> getByPath(String path);
}
