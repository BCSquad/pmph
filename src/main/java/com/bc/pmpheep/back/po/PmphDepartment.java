package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * PmphDepartment实体类
 * 
 * @author 曾庆峰
 *
 */
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
	private String dbName;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String note;

	public PmphDepartment(Long parentId, String path, String dbName, Integer sort, String note) {
		this.parentId = parentId;
		this.path = path;
		this.dbName = dbName;
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

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
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
		return "PmphDepartment [id=" + id + ", parentId=" + parentId + ", path=" + path + ", dbName=" + dbName
				+ ", sort=" + sort + ", note=" + note + "]";
	}

}
