package com.yffd.jecap.admin.domain.org.service;

import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.admin.domain.org.entity.SysOrg;
import com.yffd.jecap.admin.domain.org.repo.ISysOrgRepo;
import com.yffd.jecap.admin.domain.org.valobj.SysOrgTree;
import com.yffd.jecap.admin.infrastructure.dao.org.ISysOrgDao;
import com.yffd.jecap.common.base.exception.DataExistException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SysOrgService {
    @Autowired private ISysOrgRepo sysOrgRepo;

    private ISysOrgDao getDao() {
        return this.sysOrgRepo.getOrgDao();
    }

    /**
     * 查询所有树
     * @return
     */
    public List<SysOrgTree> queryTree() {
        List<SysOrg> list = this.getDao().queryList(new SysOrg());
        SysOrgTree tree = SysOrgTree.buildTree(list);
        if (null == tree) return Collections.EMPTY_LIST;
        return tree.getChildren();
    }

    /**
     * 查询单棵树
     * @param orgId
     * @return
     */
    public SysOrgTree queryTree(String orgId) {
        if (StringUtils.isBlank(orgId)) return null;
        SysOrg entity = this.getDao().queryById(orgId);
        if (null == entity) return null;
        String path = entity.getPath();
        if (StringUtils.isBlank(path)) path = entity.getId();
        List<SysOrg> children = this.getDao().findByPath(path);
        return SysOrgTree.buildTree(children, entity);
    }


    public void add(SysOrg org) {
        if (null == org || StringUtils.isBlank(org.getOrgCode())) throw SysException.cast("编号不能为空");
        if (this.existByOrgCode(org.getOrgCode())) throw DataExistException.cast("编号已存在");
        this.getDao().addBy(org);
    }

    @Transactional
    public void add(List<SysOrg> entityList) {
        if (CollectionUtils.isEmpty(entityList)) return;
        entityList.forEach(entity -> this.add(entity));
    }

    /**
     * 添加根节点
     * @param org
     */
    public void addRoot(SysOrg org) {
        if (null == org || StringUtils.isBlank(org.getOrgCode())) throw SysException.cast("编号不能为空");
        if (this.existByOrgCode(org.getOrgCode())) throw DataExistException.cast("编号已存在");
        org.setPid(AdminConsts.DEF_TREE_ROOT_ID);
        this.getDao().addBy(org);
    }

    /**
     * 添加子节点
     * @param org
     */
    @Transactional
    public void addChild(SysOrg org) {
        if (null == org || StringUtils.isBlank(org.getOrgCode())) throw SysException.cast("编号不能为空");
        if (this.existByOrgCode(org.getOrgCode())) throw DataExistException.cast("编号已存在");
        SysOrg parent = this.getDao().queryById(org.getPid());
        if (null == parent) throw SysException.cast("父ID不存在");
        if (StringUtils.isBlank(parent.getPath())) {
            org.setPath(parent.getId());

        } else {
            org.setPath(parent.getPath() + "," + parent.getId());
        }
        this.getDao().addBy(org);
    }

    /**
     * 只修改当前节点的信息，不包括子节点
     * @param org
     */
    public void updateById(SysOrg org) {
        if (null == org || StringUtils.isBlank(org.getId())) return;
        org.setPid(null);//父ID不可修改
        if (StringUtils.isNotBlank(org.getOrgCode())) {
            SysOrg entity = this.queryByOrgCode(org.getOrgCode());
            if (null != entity && !entity.getId().equals(org.getId())) {
                throw SysException.cast(String.format("组织编号【%s】已存在", org.getOrgCode())).prompt();
            }
        }
        this.getDao().updateById(org);
    }

    /**
     * 修改当前节点信息，以及其子节点的path
     * @param entity
     * @param pid
     */
    @Transactional
    public void updateById(SysOrg entity, String pid) {
        if (null == entity || StringUtils.isBlank(entity.getId())) return;
        SysOrg curNode = this.getDao().queryById(entity.getId());
        if (null == curNode) return;
        if (StringUtils.isBlank(pid)) {
            this.updateById(entity);//修改当前节点信息
        } else if (!pid.equals(curNode.getPid())) {//变更父节点
            SysOrg parent = this.getDao().queryById(pid);
            if (null == parent) throw SysException.cast("父ID不存在");
            String curPath = curNode.getPath();
            String newPath = StringUtils.isBlank(parent.getPath()) ? parent.getId() : parent.getPath() + "," + parent.getId();
            entity.setPath(newPath);
            entity.setPid(parent.getId());
            if (StringUtils.isNotBlank(entity.getOrgCode())) {
                SysOrg org = this.queryByOrgCode(entity.getOrgCode());
                if (null != org && !org.getId().equals(entity.getId())) {
                    throw SysException.cast(String.format("组织编号【%s】已存在", entity.getOrgCode())).prompt();
                }
            }
            this.getDao().modifyById(entity);//修改当前节点信息

            //更改子节点path、pid
            List<SysOrg> children = this.queryChildrenByPath(curNode.getPath() + "," + curNode.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                for (SysOrg child : children) {
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

    public void updatePathById(String orgId, String path) {
        if (StringUtils.isBlank(orgId)) return;
        SysOrg entity = new SysOrg();
        entity.setId(orgId);
        entity.setPath(path);
        this.updateById(entity);
    }

    @Transactional
    public int deleteById(String orgId) {
        if (null == orgId) return 0;
        // 当前节点与其子节点
        Set<String> orgIds = new HashSet<>();
        orgIds.add(orgId);//当前节点ID
        // 其子节点ID
        Set<String> childrenIds = this.queryChildrenIds(orgId);
        orgIds.addAll(childrenIds);
        // 删除节点，以及子节点
        return this.getDao().removeByIds(orgIds);
    }

    public Set<String> queryChildrenIds(String orgId) {
        List<SysOrg> list = this.queryChildren(orgId);
        if (CollectionUtils.isEmpty(list)) return Collections.EMPTY_SET;
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getId()));
        return ids;
    }

    public List<SysOrg> queryChildren(String orgId) {
        if (StringUtils.isBlank(orgId)) return Collections.EMPTY_LIST;
        SysOrg entity = this.getDao().queryById(orgId);
        if (null == entity || StringUtils.isBlank(entity.getPath())) return Collections.EMPTY_LIST;
        return this.getDao().findByPath(entity.getPath() + "," + entity.getId());
    }

    public List<SysOrg> queryChildrenByPath(String path) {
        if (StringUtils.isBlank(path)) return Collections.EMPTY_LIST;
        return this.getDao().findByPath(path);
    }

    public SysOrg queryByPid(String pid) {
        if (StringUtils.isBlank(pid)) return null;
        SysOrg entity = new SysOrg();
        entity.setPid(pid);
        return this.getDao().queryOne(entity);
    }

    public SysOrg queryByOrgCode(String orgCode) {
        if (StringUtils.isBlank(orgCode)) return null;
        return this.getDao().queryOne(new SysOrg(null, orgCode));
    }

    public boolean existByOrgCode(String orgCode) {
        return null != this.queryByOrgCode(orgCode);
    }

    public SysOrg queryById(String orgId) {
        if (StringUtils.isBlank(orgId)) return null;
        return this.getDao().queryById(orgId);
    }
}
