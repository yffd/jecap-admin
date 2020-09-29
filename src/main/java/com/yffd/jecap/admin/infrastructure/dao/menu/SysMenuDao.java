package com.yffd.jecap.admin.infrastructure.dao.menu;

import com.yffd.jecap.admin.domain.menu.entity.SysMenu;
import com.yffd.jecap.common.base.dao.mybatis.MybatisplusBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统-菜单表 Mapper 接口
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Mapper
public interface SysMenuDao extends MybatisplusBaseDao<SysMenu> {

}
