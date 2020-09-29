package com.yffd.jecap.admin.domain.org.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-组织表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
public class SysOrg implements IBaseEntity {

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
     * 组织名称
     */
    private String orgName;

    /**
     * 组织编号，唯一
     */
    private String orgCode;

    /**
     * 组织类型，1=企业、2=连锁店
     */
    private String orgType;

    /**
     * 组织排序号
     */
    private String orgSn;

    public SysOrg(String orgName, String orgCode) {
        this.orgName = orgName;
        this.orgCode = orgCode;
    }
}
