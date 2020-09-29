package com.yffd.jecap.admin.infrastructure.dao.role;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yffd.jecap.admin.domain.role.entity.SysRole;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-角色表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysRoleDao extends MybatisplusBaseDao<SysRole> {

    @Override
    default Wrapper<SysRole> getWrapper(SysRole entity) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(entity.getId())) wrapper.eq("id", entity.getId());
        if (StringUtils.isNotBlank(entity.getRoleName())) wrapper.like("role_name", entity.getRoleName());
        if (StringUtils.isNotBlank(entity.getRoleStatus())) wrapper.eq("role_status", entity.getRoleStatus());
        return wrapper;
    }
}
