package com.bc.pmpheep.back.vo;



import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 *申报表审核列表
 *@author MrYang 
 *@CreateDate 2017年11月22日 下午2:44:33
 *
 **/
@SuppressWarnings("serial")
@Alias("DeclarationListVO")
public class DeclarationListVO implements java.io.Serializable {

    //社保主键
	private Long id;
	//教材id
  	private Long materialId;
	//作家真实姓名
	private String realname;
	//作家账号
	private String username;
	//申报单位
	private String unitName;
	//工作单位
	private String orgName;
	//作家职务
	private String position;
	//作家职称
	private String title;
	//作家手机
	private String handphone;
	//作家邮箱
	private String email;	
	//所选书籍与职务
	private String chooseBooksAndPostions;
	//学校审核进度
	private Integer onlineProgress;
	//纸质表进度
    private Integer offlineProgress;
    //书籍名称
    private String textbookName;

    private String presetPosition;
    private Integer orgId;
    //提交日期
    private Timestamp commitDate;


	public String getPresetPosition() {
		return presetPosition;
	}

	public void setPresetPosition(String presetPosition) {
		this.presetPosition = presetPosition;
	}

	public String getTextbookName() {
		return textbookName;
	}

	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}

	public DeclarationListVO() {
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChooseBooksAndPostions() {
		return chooseBooksAndPostions;
	}

	public void setChooseBooksAndPostions(String chooseBooksAndPostions) {
		this.chooseBooksAndPostions = chooseBooksAndPostions;
	}

	public Integer getOnlineProgress() {
		return onlineProgress;
	}

	public void setOnlineProgress(Integer onlineProgress) {
		this.onlineProgress = onlineProgress;
	}

	public Integer getOfflineProgress() {
		return offlineProgress;
	}

	public void setOfflineProgress(Integer offlineProgress) {
		this.offlineProgress = offlineProgress;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", realname:'"
				+ realname + "', username:'" + username + "', unitName:'"
				+ unitName + "', orgName:'" + orgName + "', position:'" + position
				+ "', title:'" + title + "', handphone:'" + handphone + "', email:'"
				+ email + "', chooseBooksAndPostions:'" + chooseBooksAndPostions
				+ "', onlineProgress:" + onlineProgress + ", offlineProgress:"
				+ offlineProgress + "}";
	}


	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Timestamp getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(Timestamp commitDate) {
		this.commitDate = commitDate;
	}
}
