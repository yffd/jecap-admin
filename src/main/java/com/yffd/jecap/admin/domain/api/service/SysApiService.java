package com.yffd.jecap.admin.domain.api.service;

import com.yffd.jecap.admin.domain.api.entity.SysApi;
import com.yffd.jecap.admin.domain.api.repo.ISysApiRepo;
import com.yffd.jecap.common.base.repository.IBaseRepository;
import com.yffd.jecap.common.base.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysApiService extends AbstractBaseService<SysApi> {
    @Autowired private ISysApiRepo sysApiRepo;

    @Override
    protected IBaseRepository getRepo() {
        return sysApiRepo;
    }
}
