package com.yffd.jecap.admin.domain.api.service;

import com.yffd.jecap.admin.domain.api.entity.SysApi;
import com.yffd.jecap.admin.domain.api.repo.ISysApiRepo;
import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.common.base.dao.IBaseDao;
import com.yffd.jecap.common.base.page.PageData;
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
        if (null == api || StringUtils.isBlank(api.getApiName())) throw SysException.cast("API接口名称不能为空").prompt();
        this.getDao().addBy(api);
    }

    public void updateById(SysApi api) {
        if (null == api || StringUtils.isBlank(api.getId())) return;
        this.getDao().modifyById(api);
    }

    public void deleteById(String apiId) {
        if (StringUtils.isBlank(apiId)) return;
        this.getDao().removeById(apiId);
        this.apiPmsnService.deleteByApiId(apiId);//删除关联关系
    }

    public SysApi queryById(String apiId) {
        if (StringUtils.isBlank(apiId)) return null;
        return this.getDao().queryById(apiId);
    }

    public PageData<SysApi> queryPage(SysApi api, int pageNum, int pageSize) {
        return this.getDao().queryPage(api, pageNum, pageSize);
    }

}
