package com.yffd.jecap.admin.infrastructure.dao.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yffd.jecap.admin.domain.user.entity.SysUserRole;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-用户&角色关联表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysUserRoleDao extends MybatisplusBaseDao<SysUserRole> {

    @Override
    default Wrapper<SysUserRole> getWrapper(SysUserRole entity) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(entity.getUserId())) wrapper.eq("user_id", entity.getUserId());
        if (StringUtils.isNotBlank(entity.getRoleId())) wrapper.eq("role_id", entity.getRoleId());
        return wrapper;
    }
}
