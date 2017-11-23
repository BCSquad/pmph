package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title:社内角色-教材申报权限关联表<p>
 * <p>Description:实现类<p>
 * @author lyc
 * @date 2017年11月23日 下午2:45:53
 */
@SuppressWarnings("serial")
@Alias("PmphRoleMaterial")
public class PmphRoleMaterial implements Serializable{

	//主键
	private Long id;
	//角色id
	private Long roleId;
	//权限
	private Integer permission;
	
	public PmphRoleMaterial() {
		super();
	}

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
		return " {id:" + id + ", roleId:" + roleId + ", permission:"
				+ permission + "}";
	}
	
}
