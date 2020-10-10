package com.yffd.jecap.admin.application.dto.file;

import com.yffd.jecap.admin.domain.file.entity.SysFile;
import com.yffd.jecap.admin.domain.menu.entity.SysMenu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FileSaveDto implements Serializable {
    private static final long serialVersionUID = 1797362959212131160L;
    private SysFile file;
    private String pmsnName;
}
