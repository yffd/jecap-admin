package com.yffd.jecap.admin.domain.job.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-岗位&组织关联表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysJobOrg implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 岗位ID
     */
    private String jobId;

    /**
     * 组织ID
     */
    private String orgId;

    public SysJobOrg(String jobId, String orgId) {
        this.jobId = jobId;
        this.orgId = orgId;
    }
}
