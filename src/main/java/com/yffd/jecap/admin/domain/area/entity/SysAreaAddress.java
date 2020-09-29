package com.yffd.jecap.admin.domain.area.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-区域地址表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
public class SysAreaAddress implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 区域ID
     */
    private String areaId;

    /**
     * 地理坐标，经纬度(x,y)
     */
    private String coordinate;

    /**
     * 地址，街道、门牌号
     */
    private String address;

    /**
     * 备注
     */
    private String remarks;


}
