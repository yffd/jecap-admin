package com.yffd.jecap.admin.domain.pmsn.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-权限表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
public class SysPmsn implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 权限名称
     */
    private String pmsnName;

    /**
     * 权限类型，1=页面、2=操作、3=数据
     */
    private String pmsnType;

    /**
     * 权限状态，1=启用、0=禁用
     */
    private String pmsnStatus;

    public SysPmsn(String pmsnName, String pmsnType, String pmsnStatus) {
        this.pmsnName = pmsnName;
        this.pmsnType = pmsnType;
        this.pmsnStatus = pmsnStatus;
    }
}
