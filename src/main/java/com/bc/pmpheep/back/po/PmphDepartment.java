package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * PmphDepartment  社内部门表   实体类 --   Pmph_Department
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("PmphDepartment")
public class PmphDepartment implements Serializable {
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
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String note;
	
	public PmphDepartment(){
		
	}

	public PmphDepartment(Long id){
		this.id=id;
	}
	/**
	 * 
	 * @param parentId 上级部门id  0为根节点
	 * @param path  根节点路径  格式为0-X-Y-Z-…
	 * @param dpName   部门名称   
	 * @param sort   显示顺序
	 * @param note   备注
	 */
	public PmphDepartment(Long parentId, String path, String dpName, Integer sort, String note) {
		this.parentId = parentId;
		this.path = path;
		this.dpName = dpName;
		this.sort = sort;
		this.note = note;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDpName() {
		return dpName;
	}

	public void setDpName(String dpName) {
		this.dpName = dpName;
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
		return "{id:" + id + ", parentId:" + parentId + ", path:" + path + ", dpName:" + dpName
				+ ", sort:" + sort + ", note:" + note + "}";
	}

}
