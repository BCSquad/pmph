package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年9月25日 上午11:40:42
 *
 **/
@SuppressWarnings("serial")
@Alias("AreaTreeVO")
public class AreaTreeVO implements Serializable{
	
	//父级id
	private Long parentId = 0L;
	//id
	private Long id;
	//区域名
	private String areaName;
	//显示顺序
	private Integer sort;
	//子集
	private List<AreaTreeVO> chirldren ;
	//是否是叶子节点
	private Boolean isLeaf=true;
	
	public AreaTreeVO() {
		super();
	}
	
	public AreaTreeVO(Long id) {
		this.id=id;
	}
	/**
	 * 设置id 和父id的构造器
	 * @param id
	 * @param parentId
	 */
	public AreaTreeVO(Long id,Long parentId) {
		this.id=id;
		this.parentId=parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<AreaTreeVO> getChirldren() {
		return chirldren;
	}

	public void setChirldren(List<AreaTreeVO> chirldren) {
		this.chirldren = chirldren;
	}

	public Boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
	
}
