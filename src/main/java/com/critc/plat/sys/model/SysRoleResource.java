package com.critc.plat.sys.model;

import javax.persistence.Column;

/**
 * Author  孔垂云
 * Date  2017/6/11.
 */
public class SysRoleResource {
    private int id;//id
    @Column(name = "role_id")
    private int roleId;//角色id
    @Column(name = "resource_id")
    private int resourceId;//资源id

    @Override
    public String toString() {
        return "SysRoleResource{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", resourceId=" + resourceId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
