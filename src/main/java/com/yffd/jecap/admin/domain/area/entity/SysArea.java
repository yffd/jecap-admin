package com.yffd.jecap.admin.domain.area.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-区域表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysArea implements IBaseEntity {

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
     * 级别，1=国家、2=省、3=市、4=区
     */
    private String level;

    /**
     * 编号，唯一
     */
    private String areaCode;

    /**
     * 简称，如：京、津、翼
     */
    private String areaShortName;

    /**
     * 全称
     */
    private String areaFullName;

    /**
     * 简拼
     */
    private String areaJianpin;

    /**
     * 全拼
     */
    private String areaQuanpin;

    public SysArea(String areaCode) {
        this.areaCode = areaCode;
    }
}
