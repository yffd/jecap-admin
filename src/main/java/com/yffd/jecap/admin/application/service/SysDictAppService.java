package com.yffd.jecap.admin.application.service;

import com.yffd.jecap.admin.application.dto.dict.DictSaveDto;
import com.yffd.jecap.admin.domain.dict.service.SysDictPropsService;
import com.yffd.jecap.admin.domain.dict.service.SysDictService;
import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.common.base.result.RtnResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SysDictAppService {
    @Autowired private SysDictService dictService;
    @Autowired private SysDictPropsService dictPropsService;

    public RtnResult add(DictSaveDto dto) {
        if (null == dto || null == dto.getDict()) return RtnResult.FAIL_PARAM_ISNULL();
        this.dictService.add(dto.getDict());
        //字典属性
        if (CollectionUtils.isNotEmpty(dto.getPropsList())) {
            dto.getPropsList().forEach(props -> {
                props.setDictId(dto.getDict().getId());
                this.dictPropsService.add(props);
            });
        }
        return RtnResult.OK();
    }

    public RtnResult update(DictSaveDto dto) {
        if (null == dto || null == dto.getDict()) return RtnResult.FAIL_PARAM_ISNULL();
        String dictId = dto.getDict().getId();
        if (StringUtils.isBlank(dictId)) return RtnResult.FAIL("【字典ID】不能为空");
        this.dictService.updateById(dto.getDict());
        //字典属性
        if (CollectionUtils.isNotEmpty(dto.getPropsList())) {
            dto.getPropsList().forEach(props -> {
                if (StringUtils.isBlank(props.getId())) throw SysException.cast("【字典属性ID不能为空】");
                props.setDictId(dto.getDict().getId());
                this.dictPropsService.updateById(props);
            });
        }
        return RtnResult.OK();
    }

    public void delete(String dictId) {
        if (StringUtils.isBlank(dictId)) return;
        this.dictService.deleteById(dictId);
        this.dictPropsService.delByDictId(dictId);
    }

}
