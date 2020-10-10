package com.yffd.jecap.admin.application.dto.role;

import com.yffd.jecap.admin.domain.role.entity.SysRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class RoleSaveDto implements Serializable {
    private static final long serialVersionUID = 4255187618638359380L;

    private SysRole role;
    private Set<String> groupIds;
    private Set<String> pmsnIds;

}
