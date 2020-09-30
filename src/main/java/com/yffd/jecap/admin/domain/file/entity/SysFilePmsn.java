package com.yffd.jecap.admin.domain.file.entity;

import com.yffd.jecap.common.base.entity.IBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统-文件&权限关联表
 * </p>
 *
 * @author ZhangST
 * @since 2020-09-28
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SysFilePmsn implements IBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 权限ID
     */
    private String pmsnId;

    public SysFilePmsn(String fileId, String pmsnId) {
        this.fileId = fileId;
        this.pmsnId = pmsnId;
    }
}
