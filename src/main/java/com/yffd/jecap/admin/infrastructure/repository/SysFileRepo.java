package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.file.entity.SysFile;
import com.yffd.jecap.admin.domain.file.entity.SysFilePmsn;
import com.yffd.jecap.admin.domain.file.repo.ISysFileRepo;
import com.yffd.jecap.admin.infrastructure.dao.file.ISysFileDao;
import com.yffd.jecap.admin.infrastructure.dao.file.ISysFilePmsnDao;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysFileRepo implements ISysFileRepo {
    @Autowired private ISysFileDao fileDao;
    @Autowired private ISysFilePmsnDao filePmsnDao;

    @Override
    public IBaseDao<SysFile> getFileDao() {
        return fileDao;
    }

    @Override
    public IBaseDao<SysFilePmsn> getFilePmsnDao() {
        return filePmsnDao;
    }
}
