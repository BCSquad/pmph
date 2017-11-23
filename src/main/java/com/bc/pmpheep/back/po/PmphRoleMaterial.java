package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

@Alias("PmphRoleMaterial")
public class PmphRoleMaterial {
	
	// 主键
	private Long id;
	// 角色id
	private Long roleId;
	// 权限
	private Integer permission;

	public PmphRoleMaterial(Long roleId, Integer permission) {
		super();
		this.roleId = roleId;
		this.permission = permission;
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

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "PmphRoleMaterial [id=" + id + ", roleId=" + roleId + ", permission=" + permission + "]";
	}

}
