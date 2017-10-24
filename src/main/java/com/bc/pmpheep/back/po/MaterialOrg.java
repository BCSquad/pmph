package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 教材-机构关联表（多对多）
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:28:25
 *
 */
@SuppressWarnings("serial")
@Alias("MaterialOrg")
public class MaterialOrg implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 教材id
	 */
	private Long materialId;
	/**
	 * 机构id
	 */
	private Long orgId;
	
	public MaterialOrg() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMaterialId() {
		return materialId;
	}
	
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	
	public Long getOrgId() {
		return orgId;
	}
	
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", orgId:" + orgId
				+ "}";
	}

	
}