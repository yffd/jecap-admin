package com.yffd.jecap.admin.application.service;

import com.yffd.jecap.admin.application.dto.file.FileSaveDto;
import com.yffd.jecap.admin.domain.file.entity.SysFilePmsn;
import com.yffd.jecap.admin.domain.file.service.SysFilePmsnService;
import com.yffd.jecap.admin.domain.file.service.SysFileService;
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
public class SysFileAppService {
    @Autowired private SysFileService fileService;
    @Autowired private SysFilePmsnService filePmsnService;
    @Autowired private SysPmsnService pmsnService;

    public RtnResult add(FileSaveDto dto) {
        if (null == dto || null == dto.getFile()) return RtnResult.FAIL_PARAM_ISNULL();
        this.fileService.add(dto.getFile());
        //生成权限
        if (StringUtils.isNotBlank(dto.getPmsnName())) {
            String pmsnId = this.pmsnService.add(dto.getPmsnName(), "1", "1");
            this.filePmsnService.addRlt(dto.getFile().getId(), pmsnId);
        }
        return RtnResult.OK();
    }

    public RtnResult update(FileSaveDto dto) {
        if (null == dto || null == dto.getFile()) return RtnResult.FAIL_PARAM_ISNULL();
        String fileId = dto.getFile().getId();
        if (StringUtils.isBlank(fileId)) return RtnResult.FAIL("【文件ID】不能为空");
        this.fileService.updateById(dto.getFile());
        //更新权限
        if (StringUtils.isNotEmpty(dto.getPmsnName())) {
            SysFilePmsn entity = this.filePmsnService.queryByFileId(fileId);
            this.pmsnService.updatePmsnName(entity.getPmsnId(), dto.getPmsnName());
        }
        return RtnResult.OK();
    }

    public void delete(String fileId) {
        if (StringUtils.isBlank(fileId)) return;
        this.fileService.deleteById(fileId);
        this.filePmsnService.deleteByFileId(fileId);//删除关联关系
    }

}
