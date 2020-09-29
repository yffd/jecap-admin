package com.yffd.jecap.admin.infrastructure.dao.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yffd.jecap.admin.domain.user.entity.SysUserJob;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-用户&岗位关联表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysUserJobDao extends MybatisplusBaseDao<SysUserJob> {

    @Override
    default Wrapper<SysUserJob> getWrapper(SysUserJob entity) {
        QueryWrapper<SysUserJob> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(entity.getId())) wrapper.eq("id", entity.getId());
        if (StringUtils.isNotBlank(entity.getUserId())) wrapper.eq("user_id", entity.getUserId());
        if (StringUtils.isNotBlank(entity.getJobId())) wrapper.eq("job_id", entity.getJobId());
        return wrapper;
    }
}
