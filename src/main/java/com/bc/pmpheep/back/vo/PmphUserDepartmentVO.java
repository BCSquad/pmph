package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

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
@SuppressWarnings("serial")
@Alias("PmphUserDepartmentVO")
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
	 * 部门名称
	 */
	private String dpName;
	/**
	 * 根节点路径
	 */
	private String path;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 是否是叶子节点
	 */
	private Boolean  isLeaf=true;
	/**
	 * 下级部门
	 */
	private List<PmphUserDepartmentVO> sonDepartment =new ArrayList<PmphUserDepartmentVO>(16);
	
	public PmphUserDepartmentVO (){
		
	}
	
	public PmphUserDepartmentVO (Long id){
		this.id=id;
	}

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

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public List<PmphUserDepartmentVO> getSonDepartment() {
		return sonDepartment;
	}

	public void setSonDepartment(List<PmphUserDepartmentVO> sonDepartment) {
		this.sonDepartment = sonDepartment;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", parentId:" + parentId + ", dpName:" + dpName
				+ ", path:" + path + ", sort:" + sort + ", note:" + note
				+ ", isLeaf:" + isLeaf + ", sonDepartment:" + sonDepartment
				+ "}";
	}

	

	

}
