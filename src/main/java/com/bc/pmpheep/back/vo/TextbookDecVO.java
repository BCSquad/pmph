package com.bc.pmpheep.back.vo;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 书籍对应主编和编委的vo
 * @author Mr
 * @date 2017-11-30
 *
 */
@SuppressWarnings("serial")
@Alias("TextbookDecVO")
public class TextbookDecVO implements java.io.Serializable{
	// 作家id
	private Long userId;
	// 是否作家
	private Boolean isWriter;
	 // 书籍id
    private Long      textBookId;
    // 申报职务
    private Integer   presetPosition;
    // 遴选职务
    private Integer   chosenPosition;
    // 作家真实姓名
    private String    realname;
    // 作家工作单位
    private String    orgName;
    //对应学校id
    private Long    orgId;
    // 审核进度
    private Integer   onlineProgress;
    // 纸质表进度
    private Integer   offlineProgress;
    // 创建时间
    private Timestamp gmtCreate;
	// 主任
	private Long director;
	// 策划编辑
	private Long planningEditor;
	// 项目编辑
	private Long editorId;
	// 级别  0=普通用户/1=教师/2=作家/3=专家
	private Integer rank;
	// 申报单位
	private String declarationUnit;
	
	public String getDeclarationUnit() {
		return declarationUnit;
	}
	public void setDeclarationUnit(String declarationUnit) {
		this.declarationUnit = declarationUnit;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Boolean getIsWriter() {
		return isWriter;
	}
	public void setIsWriter(Boolean isWriter) {
		this.isWriter = isWriter;
	}
	public Long getTextBookId() {
		return textBookId;
	}
	public void setTextBookId(Long textBookId) {
		this.textBookId = textBookId;
	}
	public Integer getPresetPosition() {
		return presetPosition;
	}
	public void setPresetPosition(Integer presetPosition) {
		this.presetPosition = presetPosition;
	}
	public Integer getChosenPosition() {
		return chosenPosition;
	}
	public void setChosenPosition(Integer chosenPosition) {
		this.chosenPosition = chosenPosition;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	public Timestamp getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Long getDirector() {
		return director;
	}
	public void setDirector(Long director) {
		this.director = director;
	}
	public Long getPlanningEditor() {
		return planningEditor;
	}
	public void setPlanningEditor(Long planningEditor) {
		this.planningEditor = planningEditor;
	}
	public Long getEditorId() {
		return editorId;
	}
	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public TextbookDecVO() {
		super();
	}
	
}
