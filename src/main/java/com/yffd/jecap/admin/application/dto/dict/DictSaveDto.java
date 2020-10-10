package com.yffd.jecap.admin.application.dto.dict;

import com.yffd.jecap.admin.domain.dict.entity.SysDict;
import com.yffd.jecap.admin.domain.dict.entity.SysDictProps;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class DictSaveDto implements Serializable {
    private static final long serialVersionUID = -4312579385513257516L;

    private SysDict dict;
    private List<SysDictProps> propsList;
}