/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.vo;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.PmphRole;

/**
 * 社内用户管理视图对象
 *
 * @author L.X <gugia@qq.com>
 */
@Alias("PmphUserManagerVO")
public class PmphUserManagerVO implements java.io.Serializable {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 用户名以及真实姓名
	 */
	private String name;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 部门id
	 */
	private Long departmentId;
	/**
	 * 是否禁用
	 */
	private Boolean isDisabled;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 根节点路径
	 */
	private String path;
	/**
	 * 部门名称
	 */
	private String departmentName;
	/**
	 * 角色名称
	 */
	private List<PmphRoleVO> pmphRoles;
	/**
	 * 接受前端传入的角色id数组
	 */
	private String roleIds;
	/**
	 * 手机
	 */
	private String handphone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 显示顺序
	 */
	private int sort;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}

	/**
	 * @param realname
	 *            the realname to set
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the handphone
	 */
	public String getHandphone() {
		return handphone;
	}

	/**
	 * @param handphone
	 *            the handphone to set
	 */
	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	public List<PmphRoleVO> getPmphRoles() {
		return pmphRoles;
	}

	public void setPmphRoles(List<PmphRoleVO> pmphRoles) {
		this.pmphRoles = pmphRoles;
	}

	@Override
	public String toString() {
		return "PmphUserManagerVO [id=" + id + ", name=" + name + ", username=" + username + ", isDisabled="
				+ isDisabled + ", realname=" + realname + ", path=" + path + ", departmentName=" + departmentName
				+ ", pmphRoles=" + pmphRoles + ", handphone=" + handphone + ", email=" + email + ", note=" + note
				+ ", sort=" + sort + "]";
	}

}
