package com.yffd.jecap.admin.infrastructure.dao.area;

import com.yffd.jecap.admin.domain.area.entity.SysAreaAddress;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-区域地址表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysAreaAddressDao extends MybatisplusBaseDao<SysAreaAddress> {

}
