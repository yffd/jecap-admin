package com.yffd.jecap.admin.infrastructure.dao.dict;

import com.yffd.jecap.admin.domain.dict.entity.SysDict;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-字典表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface SysDictDao extends MybatisplusBaseDao<SysDict> {

}
