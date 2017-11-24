package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.MaterialContact;

@SuppressWarnings("serial")
@Alias("MaterialListVO")
public class MaterialListVO implements Serializable {
	// 主键
	private Long id;
	// 教材名称
	private String materialName;
	// 显示报名截止日期
	private Date deadline;
	// 实际报名截止日期
	private Date actualDeadline;
	// 联系人
	private List<MaterialContact> contacts;
	// 联系人名称（用于查询）
	private String contactUserName;
	// 当前用户id（用户查询我的）
	private Long userId;
	// 是不是我能够办理的
	private Boolean isMy;
	// 主任id
	private Long director;
	// 是否被强制结束
	private Boolean isForceEnd;
	// 是否被逻辑删除
	private Boolean isDeleted;
	// 是否已发布到前台
	private Boolean isPublished;
	// 状态（查询时使用）
	private String state;
	// 是否所有书籍已公布
	private Boolean isAllTextbookPublished;
	// 是否主任
	private Boolean isDirector;
	// 是否创建者
	private Boolean isFounder;
	// 创建者id
	private Long founderId;
	// 消息id
	private String msgId;
	// 创建到 哪个步骤
	private String materialStep;



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

	public List<MaterialContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<MaterialContact> contacts) {
		this.contacts = contacts;
	}

	public Long getDirector() {
		return director;
	}

	public void setDirector(Long director) {
		this.director = director;
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

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public Boolean getIsMy() {
		return isMy;
	}

	public void setIsMy(Boolean isMy) {
		this.isMy = isMy;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getIsAllTextbookPublished() {
		return isAllTextbookPublished;
	}

	public void setIsAllTextbookPublished(Boolean isAllTextbookPublished) {
		this.isAllTextbookPublished = isAllTextbookPublished;
	}

	public Boolean getIsDirector() {
		return isDirector;
	}

	public void setIsDirector(Boolean isDirector) {
		this.isDirector = isDirector;
	}

	public Boolean getIsFounder() {
		return isFounder;
	}

	public void setIsFounder(Boolean isFounder) {
		this.isFounder = isFounder;
	}

	public Long getFounderId() {
		return founderId;
	}

	public void setFounderId(Long founderId) {
		this.founderId = founderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMaterialStep() {
		return materialStep;
	}

	public void setMaterialStep(String materialStep) {
		this.materialStep = materialStep;
	}

}
