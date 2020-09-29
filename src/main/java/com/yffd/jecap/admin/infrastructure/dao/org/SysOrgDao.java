package com.yffd.jecap.admin.infrastructure.dao.org;

import com.yffd.jecap.admin.domain.org.entity.SysOrg;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-组织表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface SysOrgDao extends MybatisplusBaseDao<SysOrg> {

}
