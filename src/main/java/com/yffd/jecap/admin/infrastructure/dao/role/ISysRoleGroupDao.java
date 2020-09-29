package com.yffd.jecap.admin.infrastructure.dao.role;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.admin.domain.role.entity.SysRoleGroup;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-角色&组关联表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysRoleGroupDao extends MybatisplusBaseDao<SysRoleGroup> {

    @Override
    default Wrapper<SysRoleGroup> getWrapper(SysRoleGroup entity) {
        QueryWrapper<SysRoleGroup> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(entity.getId())) wrapper.eq("id", entity.getId());
        if (StringUtils.isNotBlank(entity.getRoleId())) wrapper.eq("role_id", entity.getRoleId());
        if (StringUtils.isNotBlank(entity.getGroupId())) wrapper.eq("group_id", entity.getGroupId());
        return wrapper;
    }

}
