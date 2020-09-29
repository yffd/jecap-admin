package com.yffd.jecap.admin.infrastructure.dao.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yffd.jecap.admin.domain.user.entity.SysUser;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-用户表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysUserDao extends MybatisplusBaseDao<SysUser> {

    @Override
    default Wrapper<SysUser> getWrapper(SysUser entity) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(entity.getId())) wrapper.eq("id", entity.getId());
        if (StringUtils.isNotBlank(entity.getUserName())) wrapper.eq("user_name", entity.getUserName());
        if (StringUtils.isNotBlank(entity.getUserPhone())) wrapper.eq("user_phone", entity.getUserPhone());
        if (StringUtils.isNotBlank(entity.getUserEmail())) wrapper.eq("user_email", entity.getUserEmail());
        if (StringUtils.isNotBlank(entity.getAcntName())) wrapper.eq("acnt_name", entity.getAcntName());
        if (StringUtils.isNotBlank(entity.getAcntStatus())) wrapper.eq("acnt_status", entity.getAcntStatus());

        return wrapper;
    }
}
