/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.back.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

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
	 * 用户名
	 */
	private String username;
	/**
	 * 是否禁用
	 */
	private Integer isDisabled;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 部门名称
	 */
	private String departmentName;
	/**
	 * 角色名称
	 */
	private String roleName;
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

	/**
	 * @return the isDisabled
	 */
	public Integer getIsDisabled() {
		return isDisabled;
	}

	/**
	 * @param isDisabled
	 *            the isDisabled to set
	 */
	public void setIsDisabled(Integer isDisabled) {
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "PmphUserManagerVO [id=" + id + ", username=" + username + ", isDisabled=" + isDisabled + ", realname="
				+ realname + ", departmentName=" + departmentName + ", roleName=" + roleName + ", handphone="
				+ handphone + ", email=" + email + ", note=" + note + ", sort=" + sort + "]";
	}

}
