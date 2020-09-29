package com.yffd.jecap.admin.domain.api.service;

import com.yffd.jecap.admin.domain.api.entity.SysApiPmsn;
import com.yffd.jecap.admin.domain.api.repo.ISysApiPmsnRepo;
import com.yffd.jecap.common.base.repository.IBaseRepository;
import com.yffd.jecap.common.base.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysApiPmsnService extends AbstractBaseService<SysApiPmsn> {
    @Autowired private ISysApiPmsnRepo sysApiPmsnRepo;

    @Override
    protected IBaseRepository getRepo() {
        return sysApiPmsnRepo;
    }

}
