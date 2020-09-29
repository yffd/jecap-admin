package com.yffd.jecap.admin.infrastructure.dao.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yffd.jecap.admin.domain.user.entity.SysUserLogin;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-用户登录表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysUserLoginDao extends MybatisplusBaseDao<SysUserLogin> {

    @Override
    default Wrapper<SysUserLogin> getWrapper(SysUserLogin entity) {
        QueryWrapper<SysUserLogin> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(entity.getId())) wrapper.eq("id", entity.getId());
        if (StringUtils.isNotBlank(entity.getUserId())) wrapper.eq("user_id", entity.getUserId());
        if (StringUtils.isNotBlank(entity.getLoginType())) wrapper.eq("login_type", entity.getLoginType());
        return wrapper;
    }
}
