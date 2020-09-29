package com.yffd.jecap.admin.infrastructure.dao.group;

import com.yffd.jecap.admin.domain.group.entity.SysGroup;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-组表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface SysGroupDao extends MybatisplusBaseDao<SysGroup> {

}
