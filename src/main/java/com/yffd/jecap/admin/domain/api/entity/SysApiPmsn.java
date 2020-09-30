package com.yffd.jecap.admin.domain.api.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-API&权限关联表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysApiPmsn implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * API接口ID
     */
    private String apiId;

    /**
     * 权限ID
     */
    private String pmsnId;

    public SysApiPmsn(String apiId, String pmsnId) {
        this.apiId = apiId;
        this.pmsnId = pmsnId;
    }
}
