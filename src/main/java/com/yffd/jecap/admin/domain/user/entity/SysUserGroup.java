package com.yffd.jecap.admin.domain.user.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-用户&组关联表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysUserGroup implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 组ID
     */
    private String groupId;

    public SysUserGroup(String userId, String groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
