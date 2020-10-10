package com.yffd.jecap.admin.infrastructure.repository;

import com.yffd.jecap.admin.domain.dict.entity.SysDict;
import com.yffd.jecap.admin.domain.dict.entity.SysDictProps;
import com.yffd.jecap.admin.domain.dict.repo.ISysDictRepo;
import com.yffd.jecap.admin.infrastructure.dao.dict.ISysDictDao;
import com.yffd.jecap.admin.infrastructure.dao.dict.ISysDictPropsDao;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysDictRepo implements ISysDictRepo {
    @Autowired private ISysDictDao dictDao;
    @Autowired private ISysDictPropsDao dictPropsDao;

    @Override
    public IBaseDao<SysDict> getDictDao() {
        return dictDao;
    }

    @Override
    public IBaseDao<SysDictProps> getDictPropsDao() {
        return dictPropsDao;
    }

    @Override
    public List<SysDict> queryByPath(String path) {
        return null;
    }
}
