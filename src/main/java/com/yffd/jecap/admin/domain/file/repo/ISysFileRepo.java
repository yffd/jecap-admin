package com.yffd.jecap.admin.domain.file.repo;

import com.yffd.jecap.admin.domain.file.entity.SysFile;
import com.yffd.jecap.admin.domain.file.entity.SysFilePmsn;
import com.yffd.jecap.common.base.dao.IBaseDao;

public interface ISysFileRepo {

    IBaseDao<SysFile> getFileDao();
    IBaseDao<SysFilePmsn> getFilePmsnDao();
}
