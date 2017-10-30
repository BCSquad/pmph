package com.bc.pmpheep.back.vo;

import java.util.List;

public class MaterialTypeVO {
	// 主键
	private Long id;
	// 上级类型id
	private Long parentId;
	// 根节点路径
	private String path;
	// 类型名称
	private String typeName;
	// 子级
	private List<MaterialTypeVO> childrenMaterialTypeVO;
	/**
	 * 是否是叶子节点
	 */
	private Boolean isLeaf = true;

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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<MaterialTypeVO> getChildrenMaterialTypeVO() {
		return childrenMaterialTypeVO;
	}

	public void setChildrenMaterialTypeVO(List<MaterialTypeVO> childrenMaterialTypeVO) {
		this.childrenMaterialTypeVO = childrenMaterialTypeVO;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}
