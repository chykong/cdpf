package com.critc.plat.sys.service;

import com.critc.plat.sys.dao.SysResourceDao;
import com.critc.plat.sys.model.SysResource;
import com.critc.plat.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  孔垂云
 * Date  2017/6/10.
 */
@Service
public class SysResourceService {

    @Autowired
    private SysResourceDao sysResourceDao;

    public int add(SysResource sysResource) {
        return sysResourceDao.add(sysResource);
    }

    public int update(SysResource sysResource) {
        return sysResourceDao.update(sysResource);
    }

    /**
     * 删除资源，先判断是否有下级资源，有的话提示不允许删除
     * 删除时同时删除该资源对应的功能
     *
     * @param id
     * @return
     */
    public int delete(int id) {
        if (sysResourceDao.getChildCount(id) > 0)
            return 2;
        else {
            sysResourceDao.deleteByParentId(id);
            return sysResourceDao.delete(id);
        }
    }

    public SysResource get(int id) {
        return sysResourceDao.get(id);
    }

    /**
     * 根据类型来获取所有资源
     *
     * @param type
     * @return
     */
    public List<SysResource> listByType(int type) {
        return sysResourceDao.listByType(type);
    }

    /**
     * 模块列表，递归生成，用于显示treeGrid
     *
     * @return
     */
    public List<SysResource> list() {
        List<SysResource> list = sysResourceDao.listByType(1);//获取所有模块
        List<SysResource> listRet = new ArrayList<>();
        listRet = createModuleList(list, listRet, 1);
        return listRet;
    }

    private List<SysResource> createModuleList(List<SysResource> list, List<SysResource> listRet, int parentId) {
        for (SysResource sysModule : list) {
            if (sysModule.getParentId() == parentId) {
                listRet.add(sysModule);
                if (sysModule.getCnt() > 0) {
                    listRet = createModuleList(list, listRet, sysModule.getId());
                }
            }
        }
        return listRet;
    }


    public int getChildCount(int id) {
        return sysResourceDao.getChildCount(id);
    }

    /**
     * 生成Ztree的树节点,新增模块时使用，只有模块上下级
     *
     * @return
     */
    public String createZtreeByModule() {
        List<SysResource> listModule = sysResourceDao.listByType(1);// 模块列表
        StringBuilder sb = new StringBuilder();
        for (SysResource sysModule : listModule) {
            if (sysModule.getParentId() != 0) {
                sb.append("{id : \"" + sysModule.getId() + "\",pId :\"" + sysModule.getParentId() + "\",name :\"" + sysModule.getName() + "\",open : false");
                sb.append("},");
            }
        }
        return StringUtil.subTract(sb.toString());
    }

    /**
     * 根据user_id获取所有功能菜单
     *
     * @param userId
     * @return
     */
    public List<SysResource> listByUser_id(int userId) {
        return sysResourceDao.listByUserId(userId);
    }
}
