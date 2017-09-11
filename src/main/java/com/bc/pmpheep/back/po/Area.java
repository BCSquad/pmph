package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * Area实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("Area")
public class Area implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 上级ID
	 */
	private Long prentId;
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 显示顺序
	 */
	private Integer sort;

	public Area(Long prentId, String areaName, Integer sort) {
		this.prentId = prentId;
		this.areaName = areaName;
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrentId() {
		return prentId;
	}

	public void setPrentId(Long prentId) {
		this.prentId = prentId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
