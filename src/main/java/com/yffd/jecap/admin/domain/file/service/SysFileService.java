package com.yffd.jecap.admin.domain.file.service;

import com.yffd.jecap.admin.domain.file.entity.SysFile;
import com.yffd.jecap.admin.domain.file.repo.ISysFileRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysFileService {
    @Autowired private SysFilePmsnService filePmsnService;
    @Autowired private ISysFileRepo fileRepo;

    private IBaseDao<SysFile> getDao() {
        return this.fileRepo.getFileDao();
    }

    public void add(SysFile file) {
        if (null == file) return;
        this.getDao().addBy(file);
    }

    public void updateById(SysFile file) {
        if (null == file || StringUtils.isBlank(file.getId())) return;
        this.getDao().modifyById(file);
    }

    public void delById(String fileId) {
        if (StringUtils.isBlank(fileId)) return;
        this.getDao().removeById(fileId);
        this.filePmsnService.delByFileId(fileId);//删除关联关系
    }

    public SysFile findById(String fileId) {
        if (StringUtils.isBlank(fileId)) return null;
        return this.getDao().findById(fileId);
    }

}
