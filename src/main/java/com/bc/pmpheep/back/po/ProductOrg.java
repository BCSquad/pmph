package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 临床-机构关联表（多对多）
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:28:25
 *
 */
@SuppressWarnings("serial")
@Alias("ProductOrg")
public class ProductOrg implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 教材id
	 */
	private Long productId;
	/**
	 * 机构id
	 */
	private Long orgId;

	public ProductOrg() {
		super();
	}

	public ProductOrg(Long productId, Long orgId) {
		super();
		this.productId = productId;
		this.orgId = orgId;
	}



	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getOrgId() {
		return orgId;
	}
	
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + productId + ", orgId:" + orgId
				+ "}";
	}

	
}