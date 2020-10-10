package com.yffd.jecap.admin.application.dto.menu;

import com.yffd.jecap.admin.domain.menu.entity.SysMenu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MenuSaveDto implements Serializable {
    private static final long serialVersionUID = 676784809544257381L;

    private SysMenu menu;
    private String pmsnName;
}
