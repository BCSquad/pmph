/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * <p>
 * Title:教材申报界面
 * <p>
 * 
 * @author lyc
 * @date 2017年9月29日 下午12:05:04
 */

@SuppressWarnings("serial")
@Alias("MaterialVO")
public class MaterialVO {
	//教材主键
	private Long id;
	//教材名称
	private String materialName;
	//显示报名截止时间
	private Date deadline;
	//实际报名截止时间
	private Date actualDeadline;
	//联系id
	private Long contactUserId;
	//联系人姓名
	private String contactUserName;
	//联系电话
	private String contactPhone;
	//联系邮箱
	private String contactEmail;
	//是否发布到前台（状态）
	private Boolean isPublished;
	
	public MaterialVO() {
		super();
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getActualDeadline() {
		return actualDeadline;
	}

	public void setActualDeadline(Date actualDeadline) {
		this.actualDeadline = actualDeadline;
	}

	public Long getContactUserId() {
		return contactUserId;
	}

	public void setContactUserId(Long contactUserId) {
		this.contactUserId = contactUserId;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	@Override
	public String toString() {
		return " {id:" + id + ", materialName:" + materialName + ", deadline:"
				+ deadline + ", actualDeadline:" + actualDeadline
				+ ", contactUserId:" + contactUserId + ", contactUserName:"
				+ contactUserName + ", contactPhone:" + contactPhone
				+ ", contactEmail:" + contactEmail + ", isPublished:"
				+ isPublished + "}";
	}
	
}
