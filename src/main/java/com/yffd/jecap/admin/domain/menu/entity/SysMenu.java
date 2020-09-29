package com.yffd.jecap.admin.domain.menu.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-菜单表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
public class SysMenu implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 父ID
     */
    private String pid;

    /**
     * 路径
     */
    private String path;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单显示，1=是、0=否
     */
    private String menuShow;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单地址
     */
    private String menuUrl;


}
