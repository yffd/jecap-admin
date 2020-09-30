package com.yffd.jecap.admin.domain.dict.service;

import com.yffd.jecap.admin.domain.constant.AdminConsts;
import com.yffd.jecap.admin.domain.dict.entity.SysDict;
import com.yffd.jecap.admin.domain.dict.repo.ISysDictRepo;
import com.yffd.jecap.admin.domain.dict.valobj.SysDictTree;
import com.yffd.jecap.admin.domain.exception.AdminException;
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
public class SysDictService {
    @Autowired private SysDictPropsService dictPropsService;
    @Autowired private ISysDictRepo dictRepo;

    private IBaseDao<SysDict> getDao() {
        return this.dictRepo.getDictDao();
    }

    /**
     * 查询所有树
     * @return
     */
    public List<SysDictTree> findTree() {
        List<SysDict> list = this.getDao().findList(new SysDict());
        SysDictTree tree = SysDictTree.buildTree(list);
        if (null == tree) return Collections.EMPTY_LIST;
        return tree.getChildren();
    }

    /**
     * 查询单棵树
     * @param orgId
     * @return
     */
    public SysDictTree findTree(String orgId) {
        if (StringUtils.isBlank(orgId)) return null;
        SysDict entity = this.getDao().findById(orgId);
        if (null == entity) return null;
        String path = entity.getPath();
        if (StringUtils.isBlank(path)) path = entity.getId();
        List<SysDict> children = this.dictRepo.findByPath(path);
        return SysDictTree.buildTree(children, entity);
    }


    public void add(SysDict dict) {
        if (null == dict || StringUtils.isBlank(dict.getItemCode())) throw AdminException.cast("编号不能为空");
        if (this.existByItemCode(dict.getItemCode())) throw DataExistException.cast("编号已存在");
        this.getDao().addBy(dict);
    }

    @Transactional
    public void add(List<SysDict> dictList) {
        if (CollectionUtils.isEmpty(dictList)) return;
        dictList.forEach(entity -> this.add(entity));
    }

    /**
     * 添加根节点
     * @param dict
     */
    public void addRoot(SysDict dict) {
        if (null == dict || StringUtils.isBlank(dict.getItemCode())) throw AdminException.cast("编号不能为空");
        if (this.existByItemCode(dict.getItemCode())) throw DataExistException.cast("编号已存在");
        dict.setPid(AdminConsts.DEF_TREE_ROOT_ID);
        this.getDao().addBy(dict);
    }

    /**
     * 添加子节点
     * @param dict
     */
    @Transactional
    public void addChild(SysDict dict) {
        if (null == dict || StringUtils.isBlank(dict.getItemCode())) throw AdminException.cast("编号不能为空");
        if (this.existByItemCode(dict.getItemCode())) throw DataExistException.cast("编号已存在");
        SysDict parent = this.getDao().findById(dict.getPid());
        if (null == parent) throw AdminException.cast("父ID不存在");
        if (StringUtils.isBlank(parent.getPath())) {
            dict.setPath(parent.getId());
        } else {
            dict.setPath(parent.getPath() + "," + parent.getId());
        }
        this.getDao().addBy(dict);
    }

    /**
     * 只修改当前节点的信息，不包括子节点
     * @param dict
     */
    public void updateById(SysDict dict) {
        if (null == dict || StringUtils.isBlank(dict.getId())) return;
        dict.setPid(null);//父ID不可修改
        if (StringUtils.isNotBlank(dict.getItemCode())) {
            SysDict entity = this.findByItemCode(dict.getItemCode());
            if (null != entity && !entity.getId().equals(dict.getId())) {
                throw AdminException.cast(String.format("编号【%s】已存在", dict.getItemCode())).prompt();
            }
        }
        this.getDao().modifyById(dict);
    }

    /**
     * 修改当前节点信息，以及其子节点的path
     * @param dict
     * @param pid
     */
    @Transactional
    public void updateById(SysDict dict, String pid) {
        if (null == dict || StringUtils.isBlank(dict.getId())) return;
        SysDict curNode = this.getDao().findById(dict.getId());
        if (null == curNode) return;
        if (StringUtils.isBlank(pid)) {
            this.updateById(dict);//修改当前节点信息
        } else if (!pid.equals(curNode.getPid())) {//变更父节点
            SysDict parent = this.getDao().findById(pid);
            if (null == parent) throw AdminException.cast("父ID不存在");
            String curPath = curNode.getPath();
            String newPath = StringUtils.isBlank(parent.getPath()) ? parent.getId() : parent.getPath() + "," + parent.getId();
            dict.setPath(newPath);
            dict.setPid(parent.getId());
            if (StringUtils.isNotBlank(dict.getItemCode())) {
                SysDict entity = this.findByItemCode(dict.getItemCode());
                if (null != entity && !entity.getId().equals(dict.getId())) {
                    throw AdminException.cast(String.format("编号【%s】已存在", dict.getItemCode())).prompt();
                }
            }
            this.getDao().modifyById(dict);//修改当前节点信息

            //更改子节点path、pid
            List<SysDict> children = this.findChildrenByPath(curNode.getPath() + "," + curNode.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                for (SysDict child : children) {
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

    public void updatePathById(String dictId, String path) {
        if (StringUtils.isBlank(dictId)) return;
        SysDict entity = new SysDict();
        entity.setId(dictId);
        entity.setPath(path);
        this.getDao().modifyById(entity);
    }

    @Transactional
    public void deleteById(String dictId) {
        if (null == dictId) return;
        // 当前节点与其子节点
        Set<String> dictIds = new HashSet<>();
        dictIds.add(dictId);//当前节点ID
        // 其子节点ID
        Set<String> childrenIds = this.findChildrenIds(dictId);
        dictIds.addAll(childrenIds);
        // 删除节点，以及子节点
        this.getDao().removeByIds(dictIds);
        this.dictPropsService.delByDictId(dictId);
    }

    public Set<String> findChildrenIds(String dictId) {
        List<SysDict> list = this.findChildren(dictId);
        if (CollectionUtils.isEmpty(list)) return Collections.EMPTY_SET;
        Set<String> ids = new HashSet<>();
        list.forEach(tmp -> ids.add(tmp.getId()));
        return ids;
    }

    public List<SysDict> findChildren(String dictId) {
        if (StringUtils.isBlank(dictId)) return Collections.EMPTY_LIST;
        SysDict entity = this.getDao().findById(dictId);
        if (null == entity || StringUtils.isBlank(entity.getPath())) return Collections.EMPTY_LIST;
        return this.dictRepo.findByPath(entity.getPath() + "," + entity.getId());
    }

    public List<SysDict> findChildrenByPath(String path) {
        if (StringUtils.isBlank(path)) return Collections.EMPTY_LIST;
        return this.dictRepo.findByPath(path);
    }

    public SysDict findByPid(String pid) {
        if (StringUtils.isBlank(pid)) return null;
        SysDict entity = new SysDict();
        entity.setPid(pid);
        return this.getDao().findOne(entity);
    }

    public SysDict findByItemCode(String itemCode) {
        if (StringUtils.isBlank(itemCode)) return null;
        return this.getDao().findOne(new SysDict(null, itemCode));
    }

    public boolean existByItemCode(String itemCode) {
        return null != this.findByItemCode(itemCode);
    }

}
