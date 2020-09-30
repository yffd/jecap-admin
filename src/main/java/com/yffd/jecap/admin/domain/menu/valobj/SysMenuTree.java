package com.yffd.jecap.admin.domain.menu.valobj;

import com.alibaba.fastjson.annotation.JSONField;
import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.menu.entity.SysMenu;
import com.yffd.jecap.common.base.support.tree.AbstractTreeBuilder;
import com.yffd.jecap.common.base.support.tree.Treeable;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Data
public class SysMenuTree implements Treeable<SysMenuTree> {
    private List<SysMenuTree> children;

    private String id;
    private String pid;
    private String menuName;
    private String menuShow;
    private String menuSn;
    private String menuIcon;
    private String menuUrl;

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
    public List<SysMenuTree> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<SysMenuTree> children) {
        this.children = children;
    }

    public static SysMenuTree buildTree(List<SysMenu> list) {
        if (CollectionUtils.isEmpty(list)) return null;
        SysMenu parent = new SysMenu();
        parent.setPid(AdminConsts.DEF_TREE_ROOT_ID);
        return buildTree(list, parent);
    }

    public static SysMenuTree buildTree(List<SysMenu> list, SysMenu parent) {
        if (CollectionUtils.isEmpty(list)) return null;
        AbstractTreeBuilder<SysMenu, SysMenuTree> builder = new AbstractTreeBuilder<SysMenu, SysMenuTree>() {

            @Override
            public SysMenuTree convert(SysMenu obj) {
                SysMenuTree tree = new SysMenuTree();
                tree.setId(obj.getId());
                tree.setPid(obj.getPid());
                tree.setMenuName(obj.getMenuName());
                tree.setMenuShow(obj.getMenuShow());
                tree.setMenuIcon(obj.getMenuIcon());
                tree.setMenuSn(obj.getMenuSn());
                tree.setMenuUrl(obj.getMenuUrl());
                return tree;
            }
        };
        return builder.buildTree(parent, list);
    }

}
