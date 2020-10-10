package com.yffd.jecap.admin.domain.pmsn.service;

import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.admin.domain.pmsn.entity.SysPmsn;
import com.yffd.jecap.admin.domain.pmsn.repo.ISysPmsnRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
import com.yffd.jecap.common.base.page.PageData;
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
        if (StringUtils.isAnyBlank(pmsn.getPmsnName(), pmsn.getPmsnType())) throw SysException.cast("【权限名称 | 权限类型】不能为空");
        if (StringUtils.isBlank(pmsn.getPmsnStatus())) pmsn.setPmsnStatus("1");
        this.getDao().addBy(pmsn);
    }

    /**
     *
     * @param pmsnName
     * @param pmsnType
     * @param pmsnStatus
     * @return  返回权限主键ID
     */
    public String add(String pmsnName, String pmsnType, String pmsnStatus) {
        SysPmsn pmsn = new SysPmsn(pmsnName, pmsnType, pmsnStatus);
        this.add(pmsn);
        return pmsn.getId();
    }

    public void updateById(SysPmsn pmsn) {
        if (null == pmsn || StringUtils.isBlank(pmsn.getId())) return;
        this.getDao().modifyById(pmsn);
    }

    public void deleteById(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return;
        this.getDao().removeById(pmsnId);
    }

    public SysPmsn queryById(String pmsnId) {
        if (StringUtils.isBlank(pmsnId)) return null;
        return this.getDao().queryById(pmsnId);
    }

    public PageData<SysPmsn> queryPage(SysPmsn pmsn, int pageNum, int pageSize) {
        return this.getDao().queryPage(pmsn, pageNum, pageSize);
    }

    public void updatePmsnName(String pmsnId, String pmsnName) {
        if (StringUtils.isBlank(pmsnId)) return;
        if (null == pmsnName) pmsnName = "";
        SysPmsn entity = new SysPmsn(pmsnName, null, null);
        entity.setId(pmsnId);
        this.getDao().modifyById(entity);
    }



}
