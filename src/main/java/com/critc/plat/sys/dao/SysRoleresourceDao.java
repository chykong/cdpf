package com.critc.plat.sys.dao;

import com.critc.plat.core.dao.BaseDao;
import com.critc.plat.sys.model.SysRoleResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色资源dao
 * Author  孔垂云
 * Date  2017/6/11.
 */
@Repository
public class SysRoleresourceDao extends BaseDao<SysRoleResource, SysRoleResource> {
    /**
     * 根据角色id获取所有资源
     *
     * @param roleId
     * @return
     */
    public List<SysRoleResource> listRoleResource(int roleId, int type) {
        String sql = "select * from t_sys_roleresource r,t_sys_resource s where r.resource_id=s.is and r.role_id=? and type=? ";
        return list(sql, roleId, type);
    }

    /**
     * 删除角色对应的资源
     *
     * @param roleId
     */
    public void deleteRoleResource(int roleId) {
        String sql = "delete from t_sys_resource where role_id=?";
        delete(sql, roleId);
    }

    /**
     * 新增角色对应资源
     *
     * @param roleId
     * @param resourceId
     */
    public void addRoleResource(int roleId, int resourceId) {
        String sql = "insert into t_sys_resource(id,role_id,module_id) values(seq_t_sys_resource.nextval,?,?)";
        update(sql, roleId, resourceId);
    }
}
