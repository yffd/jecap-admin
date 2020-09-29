package com.yffd.jecap.admin.domain.api.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-API接口表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
public class SysApi implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * API名称
     */
    private String apiName;

    /**
     * API地址
     */
    private String apiUrl;


}
