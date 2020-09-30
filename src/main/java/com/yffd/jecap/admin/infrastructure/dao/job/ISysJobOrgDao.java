package com.yffd.jecap.admin.infrastructure.dao.job;

import com.yffd.jecap.admin.domain.job.entity.SysJobOrg;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-岗位&组织关联表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysJobOrgDao extends MybatisplusBaseDao<SysJobOrg> {

}
