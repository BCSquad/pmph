package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * PmphRolePermission(社内角色-许可关联表（多对多）) 实体类 -- pmph_role_permission
 * 
 * @author 曾庆峰
 * 
 */
@SuppressWarnings("serial")
@Alias("PmphRolePermission")
public class PmphRolePermission implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 权限id
     */
    private Long permissionId;

    public PmphRolePermission() {
    }

    public PmphRolePermission(Long id) {
        super();
        this.id = id;
    }

    public PmphRolePermission(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public PmphRolePermission(Long id, Long roleId, Long permissionId) {
        super();
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "{id:" + id + ", roleId:" + roleId + ", permissionId:" + permissionId + "}";
    }

}
