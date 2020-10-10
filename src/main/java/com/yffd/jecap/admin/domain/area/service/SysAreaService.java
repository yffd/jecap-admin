package com.yffd.jecap.admin.domain.area.service;

import com.yffd.jecap.admin.domain.area.entity.SysArea;
import com.yffd.jecap.admin.domain.area.repo.ISysAreaRepo;
import com.yffd.jecap.admin.domain.area.valobj.SysAreaTree;
import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.exception.SysException;
import com.yffd.jecap.common.base.dao.IBaseDao;
import com.yffd.jecap.common.base.exception.DataExistException;
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
public class SysAreaService {
    @Autowired private SysAreaAddressService areaAddressService;
    @Autowired private ISysAreaRepo areaRepo;

    private IBaseDao<SysArea> getDao() {
        return this.areaRepo.getAreaDao();
    }

    /**
     * 查询所有树
     * @return
     */
    public List<SysAreaTree> queryTree() {
        List<SysArea> list = this.getDao().queryList(new SysArea());
        SysAreaTree tree = SysAreaTree.buildTree(list);
        if (null == tree) return Collections.EMPTY_LIST;
        return tree.getChildren();
    }

    /**
     * 查询单棵树
     * @param areaId
     * @return
     */
    public SysAreaTree queryTree(String areaId) {
        if (StringUtils.isBlank(areaId)) return null;
        SysArea entity = this.getDao().queryById(areaId);
        if (null == entity) return null;
        String path = entity.getPath();
        if (StringUtils.isBlank(path)) path = entity.getId();
        List<SysArea> children = this.areaRepo.queryByPath(path);
        return SysAreaTree.buildTree(children, entity);
    }

    public SysArea queryById(String areaId) {
        if (StringUtils.isBlank(areaId)) return null;
        return this.getDao().queryById(areaId);
    }

    public void add(SysArea area) {
        if (null == area || StringUtils.isBlank(area.getAreaCode())) throw SysException.cast("编号不能为空");
        if (this.existByAreaCode(area.getAreaCode())) throw DataExistException.cast("编号已存在");
        this.getDao().addBy(area);
    }

    @Transactional
    public void add(List<SysArea> areaList) {
        if (CollectionUtils.isEmpty(areaList)) return;
        areaList.forEach(entity -> this.add(entity));
    }

    /**
     * 添加根节点
     * @param area
     */
    public void addRoot(SysArea area) {
        if (null == area || StringUtils.isBlank(area.getAreaCode())) throw SysException.cast("编号不能为空");
        if (this.existByAreaCode(area.getAreaCode())) throw DataExistException.cast("编号已存在");
        area.setPid(AdminConsts.DEF_TREE_ROOT_ID);
        this.getDao().addBy(area);
    }

    /**
     * 添加子节点
     * @param area
     */
    @Transactional
    public void addChild(SysArea area) {
        if (null == area || StringUtils.isBlank(area.getAreaCode())) throw SysException.cast("编号不能为空");
        if (this.existByAreaCode(area.getAreaCode())) throw DataExistException.cast("编号已存在");
        SysArea parent = this.getDao().queryById(area.getPid());
        if (null == parent) throw SysException.cast("父ID不存在");
        if (StringUtils.isBlank(parent.getPath())) {
            area.setPath(parent.getId());
        } else {
            area.setPath(parent.getPath() + "," + parent.getId());
        }
        this.getDao().addBy(area);
    }

    /**
     * 只修改当前节点的信息，不包括子节点
     * @param area
     */
    public void updateById(SysArea area) {
        if (null == area || StringUtils.isBlank(area.getId())) return;
        area.setPid(null);//父ID不可修改
        if (StringUtils.isNotBlank(area.getAreaCode())) {
            SysArea entity = this.queryByAreaCode(area.getAreaCode());
            if (null != entity && !entity.getId().equals(area.getId())) {
                throw SysException.cast(String.format("编号【%s】已存在", area.getAreaCode())).prompt();
            }
        }
        this.getDao().modifyById(area);
    }

    /**
     * 修改当前节点信息，以及其子节点的path
     * @param area
     * @param pid
     */
    @Transactional
    public void updateById(SysArea area, String pid) {
        if (null == area || StringUtils.isBlank(area.getId())) return;
        SysArea curNode = this.getDao().queryById(area.getId());
        if (null == curNode) return;
        if (StringUtils.isBlank(pid)) {
            this.updateById(area);//修改当前节点信息
        } else if (!pid.equals(curNode.getPid())) {//变更父节点
            SysArea parent = this.getDao().queryById(pid);
            if (null == parent) throw SysException.cast("父ID不存在");
            String curPath = curNode.getPath();
            String newPath = StringUtils.isBlank(parent.getPath()) ? parent.getId() : parent.getPath() + "," + parent.getId();
            area.setPath(newPath);
            area.setPid(parent.getId());
            if (StringUtils.isNotBlank(area.getAreaCode())) {
                SysArea entity = this.queryByAreaCode(area.getAreaCode());
                if (null != entity && !entity.getId().equals(area.getId())) {
                    throw SysException.cast(String.format("编号【%s】已存在", area.getAreaCode())).prompt();
                }
            }
            this.getDao().modifyById(area);//修改当前节点信息

            //更改子节点path、pid
            List<SysArea> children = this.queryChildrenByPath(curNode.getPath() + "," + curNode.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                for (SysArea child : children) {
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

    public void updatePathById(String areaId, String path) {
        if (StringUtils.isBlank(areaId)) return;
        SysArea entity = new SysArea();
        entity.setId(areaId);
        entity.setPath(path);
        this.getDao().modifyById(entity);
    }

    @Transactional
    public void deleteById(String areaId) {
        if (null == areaId) return;
        // 当前节点与其子节点
        Set<String> dictIds = new HashSet<>();
        dictIds.add(areaId);//当前节点ID
        // 其子节点ID
        Set<String> childrenIds = this.queryChildrenIds(areaId);
        dictIds.addAll(childrenIds);
        // 删除节点，以及子节点
        this.getDao().removeByIds(dictIds);
        this.areaAddressService.delByAreaId(areaId);//删除关联关系
    }

    public Set<String> queryChildrenIds(String areaId) {
        List<SysArea> list = this.queryChildren(areaId);
        if (CollectionUtils.isEmpty(list)) return Collections.EMPTY_SET;
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getId()));
        return ids;
    }

    public List<SysArea> queryChildren(String areaId) {
        if (StringUtils.isBlank(areaId)) return Collections.EMPTY_LIST;
        SysArea entity = this.getDao().queryById(areaId);
        if (null == entity || StringUtils.isBlank(entity.getPath())) return Collections.EMPTY_LIST;
        return this.areaRepo.queryByPath(entity.getPath() + "," + entity.getId());
    }

    public List<SysArea> queryChildrenByPath(String path) {
        if (StringUtils.isBlank(path)) return Collections.EMPTY_LIST;
        return this.areaRepo.queryByPath(path);
    }

    public SysArea queryByPid(String pid) {
        if (StringUtils.isBlank(pid)) return null;
        SysArea entity = new SysArea();
        entity.setPid(pid);
        return this.getDao().queryOne(entity);
    }

    public SysArea queryByAreaCode(String areaCode) {
        if (StringUtils.isBlank(areaCode)) return null;
        return this.getDao().queryOne(new SysArea(areaCode));
    }

    public boolean existByAreaCode(String areaCode) {
        return null != this.queryByAreaCode(areaCode);
    }


}
