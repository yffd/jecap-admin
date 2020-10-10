package com.yffd.jecap.admin.application.dto.job;

import com.yffd.jecap.admin.domain.job.entity.SysJob;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class JobSaveDto implements Serializable {
    private static final long serialVersionUID = -702423296686946853L;

    private SysJob job;
    private String orgId;
}
