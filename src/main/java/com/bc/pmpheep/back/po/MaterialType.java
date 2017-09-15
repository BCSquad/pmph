package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * MaterialType  教材类型表     实体类 
 * @author mryang 
 *
 */
@SuppressWarnings("serial")
@Alias("MaterialType")
public class MaterialType implements java.io.Serializable {

	//主键
	private Long id;
	//上级类型id
	private Long parentId;
	//根节点路径
	private String path;
	//类型名称
	private String typeName;
	//显示顺序
	private Integer sort;
	//备注
	private String note;

	// Constructors

	/** default constructor */
	public MaterialType() {
	}

	/** full constructor */
	public MaterialType(Long parentId, String path, String typeName,
			Integer sort, String note) {
		this.parentId = parentId;
		this.path = path;
		this.typeName = typeName;
		this.sort = sort;
		this.note = note;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", parentId:" + parentId + ", path:" + path
				+ ", typeName:" + typeName + ", sort:" + sort + ", note:"
				+ note + "}";
	}
	
	
}