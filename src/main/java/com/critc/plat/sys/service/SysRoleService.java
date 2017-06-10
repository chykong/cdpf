package com.critc.plat.sys.service;

import com.critc.plat.core.pub.PubConfig;
import com.critc.plat.sys.dao.SysResourceDao;
import com.critc.plat.sys.dao.SysRoleDao;
import com.critc.plat.sys.dao.SysRoleresourceDao;
import com.critc.plat.sys.model.SysResource;
import com.critc.plat.sys.model.SysRole;
import com.critc.plat.sys.model.SysRoleResource;
import com.critc.plat.util.cache.EhCacheUtil;
import com.critc.plat.util.model.ComboboxVO;
import com.critc.plat.util.session.SessionUtil;
import com.critc.plat.util.session.UserSession;
import com.critc.plat.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author  孔垂云
 * Date  2017/6/10.
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysResourceDao sysResourceDao;
    @Autowired
    private PubConfig pubConfig;
    @Autowired
    private SysRoleresourceDao sysRoleresourceDao;

    /**
     * 新增角色
     *
     * @param sysRole
     * @param moduleArr
     * @param functionArr
     * @return
     */
    public int add(SysRole sysRole, String moduleArr, String functionArr) {
        int roleId = sysRoleDao.add(sysRole);
        sysRoleresourceDao.deleteRoleResource(roleId);
        String[] moduleSplit = moduleArr.split("@@");
        for (int i = 0; i < moduleSplit.length; i++) {
            if (StringUtil.isNotNullOrEmpty(moduleSplit[i])) {
                sysRoleresourceDao.addRoleResource(roleId, Integer.parseInt(moduleSplit[i]));
            }
        }
        String[] functionSplit = functionArr.split("@@");
        for (int i = 0; i < functionSplit.length; i++) {
            if (StringUtil.isNotNullOrEmpty(functionSplit[i])) {
                sysRoleresourceDao.addRoleResource(roleId, Integer.parseInt(functionSplit[i]));
            }
        }
        return 1;
    }

    /**
     * 修改角色
     *
     * @param sysRole
     * @param moduleArr
     * @param functionArr
     * @return
     */
    public int update(SysRole sysRole, String moduleArr, String functionArr) {
        sysRoleDao.update(sysRole);
        sysRoleresourceDao.deleteRoleResource(sysRole.getId());
        String[] moduleSplit = moduleArr.split("@@");
        for (int i = 0; i < moduleSplit.length; i++) {
            if (StringUtil.isNotNullOrEmpty(moduleSplit[i])) {
                sysRoleresourceDao.addRoleResource(sysRole.getId(), Integer.parseInt(moduleSplit[i]));
            }
        }
        String[] functionSplit = functionArr.split("@@");
        for (int i = 0; i < functionSplit.length; i++) {
            if (StringUtil.isNotNullOrEmpty(functionSplit[i])) {
                sysRoleresourceDao.addRoleResource(sysRole.getId(), Integer.parseInt(functionSplit[i]));
            }
        }
        EhCacheUtil.remove("sysCache", "hashRoleFunction_" + sysRole.getId());
        EhCacheUtil.remove("sysCache", "menu_" + sysRole.getId());
        return 1;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    public int delete(int id) {
        int flag = 0;
        flag = sysRoleDao.delete(id);
        if (flag == 1) {
            sysRoleresourceDao.deleteRoleResource(id);
            EhCacheUtil.remove("sysCache", "hashRoleFunction_" + id);
            EhCacheUtil.remove("sysCache", "menu_" + id);
        }
        return flag;
    }

    /**
     * 通过js来设置选中的模块和按钮
     *
     * @param roleId
     * @return
     */
    public String checkResourceAndFunction(int roleId) {
        List<SysRoleResource> listRoleResource = sysRoleresourceDao.listRoleResource(roleId,1);// 角色模块列表
        List<SysRoleResource> listRoleFunction = sysRoleresourceDao.listRoleResource(roleId,2);// 角色对应功能
        StringBuilder sb = new StringBuilder();
        for (SysRoleResource sysRoleResource : listRoleResource) {
            sb.append("$('#mod_" + sysRoleResource.getResourceId() + "').prop('checked',true);\r\n");
        }
        for (SysRoleResource sysRoleFunction : listRoleFunction) {
            sb.append("$('#function_" + sysRoleFunction.getResourceId() + "').prop('checked',true);\r\n");
        }
        return sb.toString();
    }

    public SysRole get(int id) {
        return sysRoleDao.get(id);
    }

    public List<SysRole> list() {
        return sysRoleDao.list();
    }

    public List<ComboboxVO> listCombo() {
        return sysRoleDao.listCombo();
    }

    /**
     * 判断按钮是否在角色中
     *
     * @param button_code
     * @return
     */
    public boolean checkBtnPrivilege(String button_code) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        UserSession userSession = SessionUtil.getUserSession(request);
        HashMap<String, String> hashRoleFunction = EhCacheUtil.get("sysCache",
                "hashRoleFunction_" + userSession.getRole_id());
        if (hashRoleFunction == null) {
            System.out.println("初始化角色对应功能" + userSession.getRole_id());
//            hashRoleFunction = sysResourceDao.hashRoleFunctionByRole_id(userSession.getRole_id());// 角色对应功能hash
            EhCacheUtil.put("sysCache", "hashRoleFunction_" + userSession.getRole_id(), hashRoleFunction);
        }
        if (hashRoleFunction.containsKey(button_code))
            return true;
        else
            return false;
    }

    /**
     * 校验按钮权限，防止不通过浏览器提交
     *
     * @param role_id
     * @param path
     * @return
     */
    public boolean checkAuthority(int role_id, String path) {
        HashMap<String, String> hashRoleFunction = EhCacheUtil.get("sysCache", "hashRoleFunction_" + role_id);
        if (hashRoleFunction == null) {
            System.out.println("初始化角色对应功能" + role_id);
//            hashRoleFunction = sysFunctionDao.hashRoleFunctionByRole_id(role_id);// 角色对应功能hash
            EhCacheUtil.put("sysCache", "hashRoleFunction_" + role_id, hashRoleFunction);
        }
        if (hashRoleFunction.containsValue(path))
            return true;
        else
            return false;
    }

    /**
     * 根据角色id生成该角色对应的菜单
     *
     * @param role_id
     * @return
     */
    public String createMenuStr(int role_id) {
        String menu = EhCacheUtil.get("sysCache", "menu_" + role_id);
        if (menu == null) {
            StringBuffer sb = new StringBuffer();
            List<SysResource> listResource = sysResourceDao.list();// 模块列表
            List<SysRoleResource> listRoleResource = sysRoleresourceDao.listRoleResource(role_id,1);// 角色模块列表
            List<Integer> displayResourceIdList = new ArrayList<>();
            for (SysRoleResource sysRoleResource : listRoleResource) {
                displayResourceIdList.add(sysRoleResource.getResourceId());
            }
            for (SysResource sysResource : listResource) {
                if (sysResource.getParentId() == 1 && displayResourceIdList.contains(sysResource.getId())) {
                    sb.append("<li class=\"\"><a href=\"#\" class=\"dropdown-toggle\"> <i class=\"menu-icon fa "
                            + sysResource.getIconImg() + "\"></i> <span class=\"menu-text\"> " + sysResource.getName()
                            + " </span> <b class=\"arrow fa fa-angle-down\"></b></a> <b class=\"arrow\"></b><ul class=\"submenu\">");
                    for (SysResource sysResourceChild : listResource) {
                        if (sysResourceChild.getParentId() == sysResource.getId()
                                && displayResourceIdList.contains(sysResourceChild.getId())) {
                            sb.append("<li id=\"module_" + sysResourceChild.getId() + "\" class=\"\"><a href=\""
                                    + pubConfig.getDynamicServer() + "/" + sysResourceChild.getUrl()
                                    + "\"> <i class=\"menu-icon fa fa-caret-right\"></i>" + sysResourceChild.getName()
                                    + "</a> <b class=\"arrow\"></b></li>");
                        }
                    }
                    sb.append("</ul></li>");
                }
            }
            menu = sb.toString();
            EhCacheUtil.put("sysCache", "menu_" + role_id, menu);
        }
        return menu;
    }
}