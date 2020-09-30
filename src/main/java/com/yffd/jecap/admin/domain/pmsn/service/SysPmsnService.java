package com.yffd.jecap.admin.domain.pmsn.service;

import com.yffd.jecap.admin.domain.pmsn.entity.SysPmsn;
import com.yffd.jecap.admin.domain.pmsn.repo.ISysPmsnRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPmsnService {
    @Autowired private ISysPmsnRepo pmsnRepo;

    private IBaseDao<SysPmsn> getDao() {
        return this.pmsnRepo.getPmsnDao();
    }

    public void add(SysPmsn pmsn) {
        if (null == pmsn) return;
        this.getDao().addBy(pmsn);
    }

    public void updateById(SysPmsn pmsn) {
        if (null == pmsn || StringUtils.isBlank(pmsn.getId())) return;
        this.getDao().modifyById(pmsn);
    }

    public void delById(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return;
        this.getDao().removeById(pmsnId);
    }

    public SysPmsn findById(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return null;
        return this.getDao().findById(pmsnId);
    }

}
