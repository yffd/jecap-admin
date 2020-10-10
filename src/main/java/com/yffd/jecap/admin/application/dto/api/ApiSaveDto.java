package com.yffd.jecap.admin.application.dto.api;

import com.yffd.jecap.admin.domain.api.entity.SysApi;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ApiSaveDto implements Serializable {
    private static final long serialVersionUID = 484355095240920071L;

    private SysApi api;
    private String pmsnName;
}
