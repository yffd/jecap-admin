package com.yffd.jecap.admin.domain.area.valobj;

import com.alibaba.fastjson.annotation.JSONField;
import com.yffd.jecap.admin.domain.area.entity.SysArea;
import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.common.base.support.tree.AbstractTreeBuilder;
import com.yffd.jecap.common.base.support.tree.Treeable;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Data
public class SysAreaTree implements Treeable<SysAreaTree> {
    private List<SysAreaTree> children;
    private String id;
    private String pid;
    private String level;
    private String areaCode;
    private String areaShortName;
    private String areaFullName;
    private String areaJianpin;
    private String areaQuanpin;

    @JSONField(serialize = false)
    @Override
    public Object getIdValue() {
        return id;
    }
    @JSONField(serialize = false)
    @Override
    public Object getPidValue() {
        return pid;
    }

    @Override
    public List<SysAreaTree> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<SysAreaTree> children) {
        this.children = children;
    }

    public static SysAreaTree buildTree(List<SysArea> list) {
        if (CollectionUtils.isEmpty(list)) return null;
        SysArea parent = new SysArea();
        parent.setPid(AdminConsts.DEF_TREE_ROOT_ID);
        return buildTree(list, parent);
    }

    public static SysAreaTree buildTree(List<SysArea> list, SysArea parent) {
        if (CollectionUtils.isEmpty(list)) return null;
        AbstractTreeBuilder<SysArea, SysAreaTree> builder = new AbstractTreeBuilder<SysArea, SysAreaTree>() {

            @Override
            public SysAreaTree convert(SysArea obj) {
                SysAreaTree tree = new SysAreaTree();
                tree.setId(obj.getId());
                tree.setPid(obj.getPid());
                tree.setLevel(obj.getLevel());
                tree.setAreaCode(obj.getAreaCode());
                tree.setAreaShortName(obj.getAreaShortName());
                tree.setAreaFullName(obj.getAreaFullName());
                tree.setAreaJianpin(obj.getAreaJianpin());
                tree.setAreaQuanpin(obj.getAreaQuanpin());
                return tree;
            }
        };
        return builder.buildTree(parent, list);
    }

}
