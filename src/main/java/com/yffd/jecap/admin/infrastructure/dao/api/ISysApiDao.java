package com.yffd.jecap.admin.infrastructure.dao.api;

import com.yffd.jecap.admin.domain.api.entity.SysApi;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-API接口表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysApiDao extends MybatisplusBaseDao<SysApi> {

}
