package com.yffd.jecap.admin.infrastructure.dao.file;

import com.yffd.jecap.admin.domain.file.entity.SysFilePmsn;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-文件&权限关联表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface ISysFilePmsnDao extends MybatisplusBaseDao<SysFilePmsn> {

}
