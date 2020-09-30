package com.yffd.jecap.admin.domain.api.service;

import com.yffd.jecap.admin.domain.api.entity.SysApi;
import com.yffd.jecap.admin.domain.api.repo.ISysApiRepo;
import com.yffd.jecap.admin.domain.exception.AdminException;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysApiService {
    @Autowired private SysApiPmsnService apiPmsnService;
    @Autowired private ISysApiRepo apiRepo;

    private IBaseDao<SysApi> getDao() {
        return this.apiRepo.getApiDao();
    }

    public void add(SysApi api) {
        if (null == api || StringUtils.isBlank(api.getApiName())) throw AdminException.cast("API接口名称不能为空").prompt();
        this.getDao().addBy(api);
    }

    public void updateById(SysApi api) {
        if (null == api || StringUtils.isBlank(api.getId())) return;
        this.getDao().modifyById(api);
    }

    public void delById(String apiId) {
        if (StringUtils.isBlank(apiId)) return;
        this.getDao().removeById(apiId);
        this.apiPmsnService.delByApiId(apiId);//删除关联关系
    }

}
