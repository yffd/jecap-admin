package com.yffd.jecap.admin.application.service;

import com.yffd.jecap.admin.application.dto.api.ApiSaveDto;
import com.yffd.jecap.admin.domain.api.entity.SysApiPmsn;
import com.yffd.jecap.admin.domain.api.service.SysApiPmsnService;
import com.yffd.jecap.admin.domain.api.service.SysApiService;
import com.yffd.jecap.admin.domain.pmsn.service.SysPmsnService;
import com.yffd.jecap.common.base.result.RtnResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SysApiAppService {
    @Autowired private SysApiService apiService;
    @Autowired private SysApiPmsnService apiPmsnService;
    @Autowired private SysPmsnService pmsnService;

    public RtnResult add(ApiSaveDto dto) {
        if (null == dto || null == dto.getApi()) return RtnResult.FAIL_PARAM_ISNULL();
        this.apiService.add(dto.getApi());
        //生成权限
        if (StringUtils.isNotBlank(dto.getPmsnName())) {
            String pmsnId = this.pmsnService.add(dto.getPmsnName(), "1", "1");
            this.apiPmsnService.addRlt(dto.getApi().getId(), pmsnId);
        }
        return RtnResult.OK();
    }

    public RtnResult update(ApiSaveDto dto) {
        if (null == dto || null == dto.getApi()) return RtnResult.FAIL_PARAM_ISNULL();
        String apiId = dto.getApi().getId();
        if (StringUtils.isBlank(apiId)) return RtnResult.FAIL("【菜单ID】不能为空");
        this.apiService.updateById(dto.getApi());
        //更新权限
        if (StringUtils.isNotEmpty(dto.getPmsnName())) {
            SysApiPmsn entity = this.apiPmsnService.queryByApiId(apiId);
            this.pmsnService.updatePmsnName(entity.getPmsnId(), dto.getPmsnName());
        }
        return RtnResult.OK();
    }

    public void delete(String apiId) {
        if (StringUtils.isBlank(apiId)) return;
        this.apiService.deleteById(apiId);
        this.apiPmsnService.deleteByApiId(apiId);//删除关联关系
    }

}
