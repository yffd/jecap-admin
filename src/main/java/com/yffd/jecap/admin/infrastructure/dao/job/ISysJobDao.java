package com.yffd.jecap.admin.infrastructure.dao.job;

import com.yffd.jecap.admin.domain.job.entity.SysJob;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-岗位表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysJobDao extends MybatisplusBaseDao<SysJob> {

}
