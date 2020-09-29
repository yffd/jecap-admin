package com.yffd.jecap.admin.domain.org.service;

import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.exception.AdminException;
import com.yffd.jecap.admin.domain.org.entity.SysOrg;
import com.yffd.jecap.admin.domain.org.repo.ISysOrgRepo;
import com.yffd.jecap.admin.domain.org.valobj.SysOrgTree;
import com.yffd.jecap.common.base.exception.DataExistException;
import com.yffd.jecap.common.base.repository.IBaseRepository;
import com.yffd.jecap.common.base.service.AbstractBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SysOrgService extends AbstractBaseService<SysOrg> {
    @Autowired private ISysOrgRepo sysOrgRepo;

    @Override
    protected IBaseRepository getRepo() {
        return sysOrgRepo;
    }

    /**
     * 获取所有树
     * @return
     */
    public List<SysOrgTree> getTree() {
        List<SysOrg> list = this.getRepo().getList(new SysOrg());
        SysOrgTree tree = SysOrgTree.buildTree(list);
        if (null == tree) return Collections.EMPTY_LIST;
        return tree.getChildren();
    }

    /**
     * 获取单棵树
     * @param orgId
     * @return
     */
    public SysOrgTree getTree(String orgId) {
        if (StringUtils.isBlank(orgId)) return null;
        SysOrg entity = (SysOrg) this.getRepo().getById(orgId);
        if (null == entity) return null;
        String path = entity.getPath();
        if (StringUtils.isBlank(path)) path = entity.getId();
        List<SysOrg> children = this.sysOrgRepo.getByPath(path);
        return SysOrgTree.buildTree(children, entity);
    }


    @Override
    public int addBy(SysOrg entity) {
        if (null == entity || StringUtils.isBlank(entity.getOrgCode())) throw AdminException.cast("编号不能为空");
        if (this.existByOrgCode(entity.getOrgCode())) throw DataExistException.cast("编号已存在");
        return this.sysOrgRepo.add(entity);
    }

    @Transactional
    public void add(List<SysOrg> entityList) {
        if (CollectionUtils.isEmpty(entityList)) return;
        entityList.forEach(entity -> this.addBy(entity));
    }

    /**
     * 添加根节点
     * @param entity
     */
    public void addRoot(SysOrg entity) {
        if (null == entity || StringUtils.isBlank(entity.getOrgCode())) throw AdminException.cast("编号不能为空");
        if (this.existByOrgCode(entity.getOrgCode())) throw DataExistException.cast("编号已存在");
        entity.setPid(AdminConsts.DEF_TREE_ROOT_ID);
        this.sysOrgRepo.add(entity);
    }

    /**
     * 添加子节点
     * @param entity
     */
    @Transactional
    public void addChild(SysOrg entity) {
        if (null == entity || StringUtils.isBlank(entity.getOrgCode())) throw AdminException.cast("编号不能为空");
        if (this.existByOrgCode(entity.getOrgCode())) throw DataExistException.cast("编号已存在");
        SysOrg parent = this.sysOrgRepo.getById(entity.getPid());
        if (null == parent) throw AdminException.cast("父ID不存在");
        if (StringUtils.isBlank(parent.getPath())) {
            entity.setPath(parent.getId());
        } else {
            entity.setPath(parent.getPath() + "," + parent.getId());
        }
        this.sysOrgRepo.add(entity);
    }

    /**
     * 只修改当前节点的信息，不包括子节点
     * @param entity
     * @return
     */
    @Override
    public int updateById(SysOrg entity) {
        if (null == entity || StringUtils.isBlank(entity.getId())) return 0;
        entity.setPid(null);//父ID不可修改
        entity.setOrgCode(null);//编号不可修改
        return super.updateById(entity);
    }

    /**
     * 修改当前节点信息，以及其子节点的path
     * @param entity
     * @param pid
     */
    @Transactional
    public void updateById(SysOrg entity, String pid) {
        if (null == entity || StringUtils.isBlank(entity.getId())) return;
        SysOrg curNode = this.sysOrgRepo.getById(entity.getId());
        if (null == curNode) return;
        if (StringUtils.isBlank(pid)) {
            entity.setPid(null);//父ID不可修改
            entity.setOrgCode(null);//编号不可修改
            this.sysOrgRepo.modifyById(entity);//修改当前节点信息
        } else if (!pid.equals(curNode.getPid())) {
            SysOrg parent = this.sysOrgRepo.getById(pid);
            if (null == parent) throw AdminException.cast("父ID不存在");
            String curPath = curNode.getPath();
            String newPath = StringUtils.isBlank(parent.getPath()) ? parent.getId() : parent.getPath() + "," + parent.getId();
            entity.setPath(newPath);
            entity.setPid(parent.getId());
            entity.setOrgCode(null);//编号不可修改
            this.sysOrgRepo.modifyById(entity);//修改当前节点信息

            //更改子节点path、pid
            List<SysOrg> children = this.getChildrenByPath(curNode.getPath() + "," + curNode.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                for (SysOrg child : children) {
                    if (null == child || StringUtils.isBlank(child.getPath())) continue;
                    String suffix = child.getPath().substring(curPath.length() + 1);
                    String tmp = newPath + (StringUtils.isBlank(suffix) ? "" : "," + suffix);
                    child.setPath(tmp);

                    String _pid = child.getPath().substring(child.getPath().lastIndexOf(",") + 1);
                    child.setPid(_pid);
                    this.sysOrgRepo.modifyById(child);//修改当前节点信息
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
    @Override
    public int deleteById(Serializable id) {
        if (null == id) return 0;
        String orgId = id.toString();
        // 当前节点与其子节点
        Set<String> orgIds = new HashSet<>();
        orgIds.add(orgId);//当前节点ID
        // 其子节点ID
        Set<String> childrenIds = this.getChildrenIds(orgId);
        orgIds.addAll(childrenIds);
        // 删除关系：角色-资源
        // 删除节点，以及子节点
        return this.sysOrgRepo.removeByIds(orgIds);
    }

    public Set<String> getChildrenIds(String orgId) {
        List<SysOrg> list = this.getChildren(orgId);
        if (CollectionUtils.isEmpty(list)) return Collections.EMPTY_SET;
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getId()));
        return ids;
    }

    public List<SysOrg> getChildren(String orgId) {
        if (StringUtils.isBlank(orgId)) return Collections.EMPTY_LIST;
        SysOrg entity = this.sysOrgRepo.getById(orgId);
        if (null == entity || StringUtils.isBlank(entity.getPath())) return Collections.EMPTY_LIST;
        return this.sysOrgRepo.getByPath(entity.getPath() + "," + entity.getId());
    }

    public List<SysOrg> getChildrenByPath(String path) {
        if (StringUtils.isBlank(path)) return Collections.EMPTY_LIST;
        return this.sysOrgRepo.getByPath(path);
    }

    public SysOrg getByPid(String pid) {
        if (StringUtils.isBlank(pid)) return null;
        SysOrg entity = new SysOrg();
        entity.setPid(pid);
        return this.sysOrgRepo.get(entity);
    }

    public SysOrg getByOrgCode(String orgCode) {
        if (StringUtils.isBlank(orgCode)) return null;
        return this.sysOrgRepo.get(new SysOrg(null, orgCode));
    }

    public boolean existByOrgCode(String orgCode) {
        return null != this.getByOrgCode(orgCode);
    }

}
