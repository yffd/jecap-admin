package com.yffd.jecap.admin.domain.group.service;

import com.yffd.jecap.admin.domain.exception.AdminException;
import com.yffd.jecap.admin.domain.group.entity.SysGroup;
import com.yffd.jecap.admin.domain.group.repo.ISysGroupRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysGroupService {
    @Autowired private ISysGroupRepo groupRepo;

    private IBaseDao<SysGroup> getDao() {
        return groupRepo.getGroupDao();
    }

    public void add(SysGroup group) {
        if (null == group || StringUtils.isBlank(group.getGroupName())) throw AdminException.cast("组名称不能为空").prompt();
        if (StringUtils.isNotBlank(group.getGroupCode()) && this.existByGroupCode(group.getGroupCode())) {
            throw AdminException.cast(String.format("组编号【%s】已存在", group.getGroupCode())).prompt();
        }
        this.getDao().addBy(group);
    }

    public void updateById(SysGroup group) {
        if (null == group || StringUtils.isBlank(group.getId())) return;
        if (StringUtils.isNotBlank(group.getGroupCode())) {
            SysGroup entity = this.findByGroupCode(group.getGroupCode());
            if (null != entity && !entity.getId().equals(group.getId())) {
                throw AdminException.cast(String.format("组编号【%s】已存在", group.getGroupCode())).prompt();
            }
        }
        this.getDao().modifyById(group);
    }

    public void delById(String groupId) {
        if (StringUtils.isBlank(groupId)) return;
        this.getDao().removeById(groupId);
    }

    public boolean existByGroupCode(String groupCode) {
        return null != this.findByGroupCode(groupCode);
    }

    public SysGroup findByGroupCode(String groupCode) {
        if (StringUtils.isBlank(groupCode)) return null;
        return this.getDao().findOne(new SysGroup(null, groupCode));
    }

    public SysGroup findById(String groupId) {
        if (StringUtils.isBlank(groupId)) return null;
        return this.getDao().findById(groupId);
    }

}
