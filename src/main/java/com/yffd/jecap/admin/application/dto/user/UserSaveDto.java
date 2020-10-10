package com.yffd.jecap.admin.application.dto.user;

import com.yffd.jecap.admin.domain.user.entity.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserSaveDto implements Serializable {
    private static final long serialVersionUID = -118408338811237538L;

    private SysUser user;
    private Set<String> roleIds;
    private Set<String> groupIds;
    private Set<String> jobIds;
}
