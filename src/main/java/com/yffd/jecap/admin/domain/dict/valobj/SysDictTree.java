package com.yffd.jecap.admin.domain.dict.valobj;

import com.alibaba.fastjson.annotation.JSONField;
import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.dict.entity.SysDict;
import com.yffd.jecap.common.base.support.tree.AbstractTreeBuilder;
import com.yffd.jecap.common.base.support.tree.Treeable;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Data
public class SysDictTree implements Treeable<SysDictTree> {
    private List<SysDictTree> children;

    private String id;
    private String pid;
    private String itemName;
    private String itemCode;
    private String itemSn;
    private String propsFlag;

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
    public List<SysDictTree> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<SysDictTree> children) {
        this.children = children;
    }

    public static SysDictTree buildTree(List<SysDict> list) {
        if (CollectionUtils.isEmpty(list)) return null;
        SysDict parent = new SysDict();
        parent.setPid(AdminConsts.DEF_TREE_ROOT_ID);
        return buildTree(list, parent);
    }

    public static SysDictTree buildTree(List<SysDict> list, SysDict parent) {
        if (CollectionUtils.isEmpty(list)) return null;
        AbstractTreeBuilder<SysDict, SysDictTree> builder = new AbstractTreeBuilder<SysDict, SysDictTree>() {

            @Override
            public SysDictTree convert(SysDict obj) {
                SysDictTree tree = new SysDictTree();
                tree.setId(obj.getId());
                tree.setPid(obj.getPid());
                tree.setItemName(obj.getItemName());
                tree.setItemCode(obj.getItemCode());
                tree.setItemSn(obj.getItemSn());
                tree.setPropsFlag(obj.getPropsFlag());
                return tree;
            }
        };
        return builder.buildTree(parent, list);
    }
}
