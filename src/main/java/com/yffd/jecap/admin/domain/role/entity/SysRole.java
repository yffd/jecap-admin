package com.yffd.jecap.admin.domain.role.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-角色表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
public class SysRole implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色状态，1=启用、0=禁用
     */
    private String roleStatus;


}
