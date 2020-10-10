package com.yffd.jecap.admin.domain.group.service;

import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.admin.domain.group.entity.SysGroup;
import com.yffd.jecap.admin.domain.group.repo.ISysGroupRepo;
import com.yffd.jecap.common.base.dao.IBaseDao;
import com.yffd.jecap.common.base.page.PageData;
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
        if (null == group || StringUtils.isBlank(group.getGroupName())) throw SysException.cast("组名称不能为空").prompt();
        if (StringUtils.isNotBlank(group.getGroupCode()) && this.existByGroupCode(group.getGroupCode())) {
            throw SysException.cast(String.format("组编号【%s】已存在", group.getGroupCode())).prompt();
        }
        this.getDao().addBy(group);
    }

    public void updateById(SysGroup group) {
        if (null == group || StringUtils.isBlank(group.getId())) return;
        if (StringUtils.isNotBlank(group.getGroupCode())) {
            SysGroup entity = this.queryByGroupCode(group.getGroupCode());
            if (null != entity && !entity.getId().equals(group.getId())) {
                throw SysException.cast(String.format("组编号【%s】已存在", group.getGroupCode())).prompt();
            }
        }
        this.getDao().modifyById(group);
    }

    public void deleteById(String groupId) {
        if (StringUtils.isBlank(groupId)) return;
        this.getDao().removeById(groupId);
    }

    public boolean existByGroupCode(String groupCode) {
        return null != this.queryByGroupCode(groupCode);
    }

    public SysGroup queryByGroupCode(String groupCode) {
        if (StringUtils.isBlank(groupCode)) return null;
        return this.getDao().queryOne(new SysGroup(null, groupCode));
    }

    public SysGroup queryById(String groupId) {
        if (StringUtils.isBlank(groupId)) return null;
        return this.getDao().queryById(groupId);
    }

    public PageData<SysGroup> queryPage(SysGroup group, int pageNum, int pageSize) {
        return this.getDao().queryPage(group, pageNum, pageSize);
    }

}
