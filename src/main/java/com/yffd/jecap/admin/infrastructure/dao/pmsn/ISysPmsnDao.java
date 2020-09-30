package com.yffd.jecap.admin.infrastructure.dao.pmsn;

import com.yffd.jecap.admin.domain.pmsn.entity.SysPmsn;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-权限表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysPmsnDao extends MybatisplusBaseDao<SysPmsn> {

}
