package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * OrgType实体类
 * 
 * @author 曾庆峰
 *
 */
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

}
