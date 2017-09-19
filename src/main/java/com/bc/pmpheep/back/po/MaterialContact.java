package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * MaterialContact  教材教材联系人表（一对多）表    实体类 
 * @author mryang 
 *
 */
@SuppressWarnings("serial")
@Alias("MaterialContact")
public class MaterialContact implements java.io.Serializable {
	//主键
	private Long id;
	//教材id
	private Long materialId;
	//联系人id
	private Long contactUserId;
	//联系人姓名
	private String contactUserName;
	//联系电话
	private String contactPhone;
	//联系邮箱
	private String contactEmail;

	// Constructors

	/** default constructor */
	public MaterialContact() {
	}
	

	public MaterialContact(Long id) {
		super();
		this.id = id;
	}


	/** full constructor */
	public MaterialContact(Long materialId, Long contactUserId,
			String contactUserName, String contactPhone, String contactEmail) {
		this.materialId = materialId;
		this.contactUserId = contactUserId;
		this.contactUserName = contactUserName;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Long getContactUserId() {
		return this.contactUserId;
	}

	public void setContactUserId(Long contactUserId) {
		this.contactUserId = contactUserId;
	}

	public String getContactUserName() {
		return this.contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", contactUserId:"
				+ contactUserId + ", contactUserName:" + contactUserName
				+ ", contactPhone:" + contactPhone + ", contactEmail:"
				+ contactEmail + "}";
	}
	
	
}