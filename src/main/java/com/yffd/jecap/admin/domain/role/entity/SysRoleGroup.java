package com.yffd.jecap.admin.domain.role.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-角色&组关联表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysRoleGroup implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 组ID
     */
    private String groupId;

    public SysRoleGroup(String roleId, String groupId) {
        this.roleId = roleId;
        this.groupId = groupId;
    }
}
