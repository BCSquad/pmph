package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
/**
 * PmphRolePermission 实体类
 * @author 曾庆峰
 *
 */
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

    public PmphRolePermission(Long roleId, Long permissionId) {
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
	return "PmphRolePermission [id=" + id + ", roleId=" + roleId + ", permissionId=" + permissionId + "]";
    }

}
