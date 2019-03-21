package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.util.DateUtil;
import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.MaterialContact;

@SuppressWarnings("serial")
@Alias("MaterialListVO")
public class MaterialListVO implements Serializable {
	// 主键
	private Long id;
	// 教材名称
	@ExcelHeader(header = "教材名称")
	private String materialName;
	// 显示报名截止日期 导出格式
	@ExcelHeader(header = "显示报名截止日期")
	private String excelDeadline;
	// 显示报名截止日期
	private Date deadline;
	// 实际报名截止日期 导出格式
	@ExcelHeader(header = "实际报名截止日期")
	private String excelActualDeadline;
	// 实际报名截止日期
	private Date actualDeadline;
	//发布日期 导出格式
	@ExcelHeader(header = "发布日期")
	private String excelGmtCreate;
	//发布日期
	private Date gmtCreate;
	// 创建者姓名
	@ExcelHeader(header = "创建人")
	private String founderName;
	//部门名称
	@ExcelHeader(header = "创建部门")
	private String dpName;
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
	// 我对这本教材的权限 (八位数二进制)
	private String myPower;
	// 项目编辑权限
	private Integer projectPermission;
	// 策划编辑权限
	private Integer planPermission;


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
		this.excelDeadline = DateUtil.date2Str(deadline,"yyyy-MM-dd");
	}

	public Date getActualDeadline() {
		return actualDeadline;
	}

	public void setActualDeadline(Date actualDeadline) {
		this.actualDeadline = actualDeadline;
		this.excelActualDeadline = DateUtil.date2Str(actualDeadline,"yyyy-MM-dd");
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

	public String getFounderName() {
		return founderName;
	}

	public void setFounderName(String founderName) {
		this.founderName = founderName;
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

	public String getMyPower() {
		return myPower;
	}

	public void setMyPower(String myPower) {
		this.myPower = myPower;
	}

	public Integer getProjectPermission() {
		return projectPermission;
	}

	public void setProjectPermission(Integer projectPermission) {
		this.projectPermission = projectPermission;
	}

	public Integer getPlanPermission() {
		return planPermission;
	}

	public void setPlanPermission(Integer planPermission) {
		this.planPermission = planPermission;
	}


	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
		this.excelGmtCreate = DateUtil.date2Str(gmtCreate,"yyyy-MM-dd");
	}

	public String getDpName() {
		return dpName;
	}

	public void setDpName(String dpName) {
		this.dpName = dpName;
	}

	public String getExcelDeadline() {
		return excelDeadline;
	}

	public void setExcelDeadline(String excelDeadline) {
		this.excelDeadline = excelDeadline;
	}

	public String getExcelActualDeadline() {
		return excelActualDeadline;
	}

	public void setExcelActualDeadline(String excelActualDeadline) {
		this.excelActualDeadline = excelActualDeadline;
	}

	public String getExcelGmtCreate() {
		return excelGmtCreate;
	}

	public void setExcelGmtCreate(String excelGmtCreate) {
		this.excelGmtCreate = excelGmtCreate;
	}

	public Boolean getMy() {
		return isMy;
	}

	public void setMy(Boolean my) {
		isMy = my;
	}

	public Boolean getForceEnd() {
		return isForceEnd;
	}

	public void setForceEnd(Boolean forceEnd) {
		isForceEnd = forceEnd;
	}

	public Boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean deleted) {
		isDeleted = deleted;
	}

	public Boolean getPublished() {
		return isPublished;
	}

	public void setPublished(Boolean published) {
		isPublished = published;
	}

	public Boolean getAllTextbookPublished() {
		return isAllTextbookPublished;
	}

	public void setAllTextbookPublished(Boolean allTextbookPublished) {
		isAllTextbookPublished = allTextbookPublished;
	}

	public void setDirector(Boolean director) {
		isDirector = director;
	}

	public Boolean getFounder() {
		return isFounder;
	}

	public void setFounder(Boolean founder) {
		isFounder = founder;
	}
}
