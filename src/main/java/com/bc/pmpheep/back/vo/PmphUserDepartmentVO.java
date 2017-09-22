package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 功能描述：社内用户管理页面机构VO
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年9月21日
 * 
 */
public class PmphUserDepartmentVO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 上级部门id
	 */
	private Long parentId;
	/**
	 * 根节点路径
	 */
	private String path;
	/**
	 * 部门名称
	 */
	private String dpName;
	/**
	 * 下级部门
	 */
	private List<PmphUserDepartmentVO> sonDepartment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDpName() {
		return dpName;
	}

	public void setDpName(String dpName) {
		this.dpName = dpName;
	}

	public List<PmphUserDepartmentVO> getSonDepartment() {
		return sonDepartment;
	}

	public void setSonDepartment(List<PmphUserDepartmentVO> sonDepartment) {
		this.sonDepartment = sonDepartment;
	}

	@Override
	public String toString() {
		return "PmphUserDepartmentVO [id=" + id + ", parentId=" + parentId + ", dpName=" + dpName + ", sonDepartment="
				+ sonDepartment + "]";
	}

}
