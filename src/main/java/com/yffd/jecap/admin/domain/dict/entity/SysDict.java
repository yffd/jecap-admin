package com.yffd.jecap.admin.domain.dict.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-字典表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysDict implements IBaseEntity {

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
     * 项名称
     */
    private String itemName;

    /**
     * 项编号
     */
    private String itemCode;

    /**
     * 排序号
     */
    private String itemSn;

    /**
     * 属性标识，0=没有属性、1=有属性
     */
    private String propsFlag;

    public SysDict(String itemName, String itemCode) {
        this.itemName = itemName;
        this.itemCode = itemCode;
    }
}
