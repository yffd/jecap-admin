package com.yffd.jecap.admin.domain.menu.service;

import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.exception.AdminException;
import com.yffd.jecap.admin.domain.menu.entity.SysMenu;
import com.yffd.jecap.admin.domain.menu.repo.ISysMenuRepo;
import com.yffd.jecap.admin.domain.menu.valobj.SysMenuTree;
import com.yffd.jecap.common.base.dao.IBaseDao;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuService {
    @Autowired private SysMenuPmsnService menuPmsnService;
    @Autowired private ISysMenuRepo menuRepo;

    private IBaseDao<SysMenu> getDao() {
        return this.menuRepo.getMenuDao();
    }

    /**
     * 查询所有树
     * @return
     */
    public List<SysMenuTree> findTree() {
        List<SysMenu> list = this.getDao().findList(new SysMenu());
        SysMenuTree tree = SysMenuTree.buildTree(list);
        if (null == tree) return Collections.EMPTY_LIST;
        return tree.getChildren();
    }

    /**
     * 查询单棵树
     * @param menuId
     * @return
     */
    public SysMenuTree findTree(String menuId) {
        if (StringUtils.isBlank(menuId)) return null;
        SysMenu entity = this.getDao().findById(menuId);
        if (null == entity) return null;
        String path = entity.getPath();
        if (StringUtils.isBlank(path)) path = entity.getId();
        List<SysMenu> children = this.menuRepo.findByPath(path);
        return SysMenuTree.buildTree(children, entity);
    }


    public void add(SysMenu menu) {
        if (null == menu || StringUtils.isBlank(menu.getMenuName())) throw AdminException.cast("菜单名称不能为空");
        this.getDao().addBy(menu);
    }

    @Transactional
    public void add(List<SysMenu> menuList) {
        if (CollectionUtils.isEmpty(menuList)) return;
        menuList.forEach(entity -> this.getDao().addBy(entity));
    }

    /**
     * 添加根节点
     * @param menu
     */
    public void addRoot(SysMenu menu) {
        if (null == menu || StringUtils.isBlank(menu.getMenuName())) throw AdminException.cast("菜单名称不能为空");
        menu.setPid(AdminConsts.DEF_TREE_ROOT_ID);
        this.getDao().addBy(menu);
    }

    /**
     * 添加子节点
     * @param menu
     */
    @Transactional
    public void addChild(SysMenu menu) {
        if (null == menu || StringUtils.isBlank(menu.getMenuName())) throw AdminException.cast("菜单名称不能为空");
        SysMenu parent = this.getDao().findById(menu.getPid());
        if (null == parent) throw AdminException.cast("父ID不存在");
        if (StringUtils.isBlank(parent.getPath())) {
            menu.setPath(parent.getId());
        } else {
            menu.setPath(parent.getPath() + "," + parent.getId());
        }
        this.getDao().addBy(menu);
    }

    /**
     * 只修改当前节点的信息，不包括子节点
     * @param menu
     */
    public void updateById(SysMenu menu) {
        if (null == menu || StringUtils.isBlank(menu.getId())) return;
        menu.setPid(null);//父ID不可修改
        this.getDao().modifyById(menu);
    }

    /**
     * 修改当前节点信息，以及其子节点的path
     * @param menu
     * @param pid
     */
    @Transactional
    public void updateById(SysMenu menu, String pid) {
        if (null == menu || StringUtils.isBlank(menu.getId())) return;
        SysMenu curNode = this.getDao().findById(menu.getId());
        if (null == curNode) return;
        if (StringUtils.isBlank(pid)) {
            this.getDao().modifyById(menu);//修改当前节点信息
        } else if (!pid.equals(curNode.getPid())) {//变更父节点
            SysMenu parent = this.getDao().findById(pid);
            if (null == parent) throw AdminException.cast("父ID不存在");
            String curPath = curNode.getPath();
            String newPath = StringUtils.isBlank(parent.getPath()) ? parent.getId() : parent.getPath() + "," + parent.getId();
            menu.setPath(newPath);
            menu.setPid(parent.getId());
            this.getDao().modifyById(menu);//修改当前节点信息

            //更改子节点path、pid
            List<SysMenu> children = this.findChildrenByPath(curNode.getPath() + "," + curNode.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                for (SysMenu child : children) {
                    if (null == child || StringUtils.isBlank(child.getPath())) continue;
                    String suffix = child.getPath().substring(curPath.length() + 1);
                    String tmp = newPath + (StringUtils.isBlank(suffix) ? "" : "," + suffix);
                    child.setPath(tmp);

                    String _pid = child.getPath().substring(child.getPath().lastIndexOf(",") + 1);
                    child.setPid(_pid);
                    this.getDao().modifyById(child);//修改当前节点信息
                }
            }
        }
    }

    public void updatePathById(String menuId, String path) {
        if (StringUtils.isBlank(menuId)) return;
        SysMenu entity = new SysMenu();
        entity.setId(menuId);
        entity.setPath(path);
        this.getDao().removeById(entity);
    }

    @Transactional
    public void deleteById(String menuId) {
        if (null == menuId) return;
        // 当前节点与其子节点
        Set<String> menuIds = new HashSet<>();
        menuIds.add(menuId);//当前节点ID
        // 其子节点ID
        Set<String> childrenIds = this.findChildrenIds(menuId);
        menuIds.addAll(childrenIds);
        // 删除节点，以及子节点
        this.getDao().removeByIds(menuIds);
        this.menuPmsnService.delByMenuId(menuId);//删除关联关系
    }

    public Set<String> findChildrenIds(String orgId) {
        List<SysMenu> list = this.findChildren(orgId);
        if (CollectionUtils.isEmpty(list)) return Collections.EMPTY_SET;
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getId()));
        return ids;
    }

    public List<SysMenu> findChildren(String menuId) {
        if (StringUtils.isBlank(menuId)) return Collections.EMPTY_LIST;
        SysMenu entity = this.getDao().findById(menuId);
        if (null == entity || StringUtils.isBlank(entity.getPath())) return Collections.EMPTY_LIST;
        return this.menuRepo.findByPath(entity.getPath() + "," + entity.getId());
    }

    public List<SysMenu> findChildrenByPath(String path) {
        if (StringUtils.isBlank(path)) return Collections.EMPTY_LIST;
        return this.menuRepo.findByPath(path);
    }

    public SysMenu findByPid(String pid) {
        if (StringUtils.isBlank(pid)) return null;
        SysMenu entity = new SysMenu();
        entity.setPid(pid);
        return this.getDao().findOne(entity);
    }


}
