package com.yffd.jecap.admin.infrastructure.dao.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yffd.jecap.admin.domain.user.entity.SysUserGroup;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-用户&组关联表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysUserGroupDao extends MybatisplusBaseDao<SysUserGroup> {

    @Override
    default Wrapper<SysUserGroup> getWrapper(SysUserGroup entity) {
        QueryWrapper<SysUserGroup> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(entity.getId())) wrapper.eq("id", entity.getId());
        if (StringUtils.isNotBlank(entity.getUserId())) wrapper.eq("user_id", entity.getUserId());
        if (StringUtils.isNotBlank(entity.getGroupId())) wrapper.eq("group_id", entity.getGroupId());
        return wrapper;
    }
}
