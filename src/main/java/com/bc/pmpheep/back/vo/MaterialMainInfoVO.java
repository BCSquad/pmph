package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年12月14日 上午9:43:30
 *
 **/
@SuppressWarnings("serial")
@Alias("MaterialMainInfoVO")
public class MaterialMainInfoVO implements java.io.Serializable {
	// 主键
    private Long      id;
    // 教材名称
    private String    materialName;
	// 是否已发布到前台
    private Boolean   isPublished;
    // 是否所有书籍已公布
    private Boolean   isAllTextbookPublished;
    // 是否被强制结束
    private Boolean   isForceEnd;
    // 是否被逻辑删除
    private Boolean   isDeleted;
    // 我对这本教材的权限 (八位数二进制)
    private String myPower;
    
	public MaterialMainInfoVO() {
		super();
	}

	public MaterialMainInfoVO(Long id, 
							String materialName,
							Boolean isPublished,
							Boolean isAllTextbookPublished,
							Boolean isForceEnd, 
							Boolean isDeleted, 
							String myPower) {
		super();
		this.id = id;
		this.materialName = materialName;
		this.isPublished = isPublished;
		this.isAllTextbookPublished = isAllTextbookPublished;
		this.isForceEnd = isForceEnd;
		this.isDeleted = isDeleted;
		this.myPower = myPower;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Boolean getIsAllTextbookPublished() {
		return isAllTextbookPublished;
	}

	public void setIsAllTextbookPublished(Boolean isAllTextbookPublished) {
		this.isAllTextbookPublished = isAllTextbookPublished;
	}

	public Boolean getIsForceEnd() {
		return isForceEnd;
	}

	public void setIsForceEnd(Boolean isForceEnd) {
		this.isForceEnd = isForceEnd;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMyPower() {
		return myPower;
	}

	public void setMyPower(String myPower) {
		this.myPower = myPower;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", materialName:" + materialName
				+ ", isPublished:" + isPublished + ", isAllTextbookPublished:"
				+ isAllTextbookPublished + ", isForceEnd:" + isForceEnd
				+ ", isDeleted:" + isDeleted + ", myPower:" + myPower + "}";
	}
    
    
    
    
    
}
