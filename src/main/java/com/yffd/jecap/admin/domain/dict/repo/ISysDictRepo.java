package com.yffd.jecap.admin.domain.dict.repo;

import com.yffd.jecap.admin.domain.dict.entity.SysDict;
import com.yffd.jecap.admin.domain.dict.entity.SysDictProps;
import com.yffd.jecap.common.base.dao.IBaseDao;

import java.util.List;

public interface ISysDictRepo {

    IBaseDao<SysDict> getDictDao();
    IBaseDao<SysDictProps> getDictPropsDao();

    List<SysDict> queryByPath(String path);
}
