package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * OrgType (机构类型表) 实体类 -- Org_Type
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("OrgType")
public class OrgType implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 机构类型名称
	 */
	private String typeName;
	/**
	 * 显示顺序
	 */
	private Integer sort;

	public OrgType() {

	}

	public OrgType(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @param typeName
	 *            机构类型名称
	 * @param sort
	 *            显示顺序
	 */
	public OrgType(String typeName, Integer sort) {
		this.typeName = typeName;
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", typeName:" + typeName + ", sort:" + sort + "}";
	}

}
