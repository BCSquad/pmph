package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.util.Const;
import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：查看选题申报的VO
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月22日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("TopicDeclarationVO")
public class TopicDeclarationVO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 书名
	 */
	@ExcelHeader(header = "选题名称")
	private String bookname;
	/**
	 * 作家姓名
	 */
	@ExcelHeader(header = "作者")
	private String realname;
	/**
	 * 作家用户名
	 */
	@ExcelHeader(header = "作者账号")
	private String submitUser;
	/**
	 * 提交日期(前台到后台的日期)
	 */
	@ExcelHeader(header = "提交日期")
	private Timestamp submitTime;
	/**
	 * 预计交稿时间
	 */
	@ExcelHeader(header = "预计交稿日期")
	private Timestamp deadline;
	/**
	 * 图书类别 0=专著/1=基础理论/2=论文集/3=科普/4=应用技术/5=工具书/6=其他
	 */
	private Integer type;
	/**
	 * 图书类别
	 */
	@ExcelHeader(header = "图书类别")
	private String typeName;

	/**
	 * 提交日期(查询起)
	 */
	private Timestamp submitTime1;
	/**
	 * 提交日期(查询止)
	 */
	private Timestamp submitTime2;
	/**
	 * 审核编辑的真实姓名
	 */
	private String editorName;
	/**
	 * 审核时间
	 */
	private Timestamp authDate;
	/**
	 * 审核进度
	 */
	private Integer authProgress;
	/**
	 * 退回原因/审核意见
	 */
	private String authFeedback;
	/**
	 * 是否有运维人员受理
	 */
	private Boolean isOptsHandling;
	/**
	 * 是否由主任受理
	 */
	private Boolean isDirectorHandling;
	/**
	 * 是否由编辑受理
	 */
	private Boolean isEditorHandling;
	/**
	 * 编辑是否接受办理
	 */
	private Boolean isAccepted;
	/**
	 * 选题申报状态
	 */
	private String state;
	/**
	 * 详细状态
	 */
	private String stateDeail;

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
		if(type!=null && type>=0 && type < Const.TOPIC_TYPES.length){
			this.typeName = Const.TOPIC_TYPES[type];
		}else{
			this.typeName = "";
		}
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public Timestamp getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Timestamp authDate) {
		this.authDate = authDate;
	}

	public Boolean getIsOptsHandling() {
		return isOptsHandling;
	}

	public void setIsOptsHandling(Boolean isOptsHandling) {
		this.isOptsHandling = isOptsHandling;
	}

	public Boolean getIsDirectorHandling() {
		return isDirectorHandling;
	}

	public void setIsDirectorHandling(Boolean isDirectorHandling) {
		this.isDirectorHandling = isDirectorHandling;
	}

	public Boolean getIsEditorHandling() {
		return isEditorHandling;
	}

	public void setIsEditorHandling(Boolean isEditorHandling) {
		this.isEditorHandling = isEditorHandling;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateDeail() {
		return stateDeail;
	}

	public void setStateDeail(String stateDeail) {
		this.stateDeail = stateDeail;
	}

	public Integer getAuthProgress() {
		return authProgress;
	}

	public void setAuthProgress(Integer authProgress) {
		this.authProgress = authProgress;
	}

	public String getAuthFeedback() {
		return authFeedback;
	}

	public void setAuthFeedback(String authFeedback) {
		this.authFeedback = authFeedback;
	}

	public TopicDeclarationVO(Long id, String realname, String bookname, Timestamp deadline, Integer type,
			String typeName, Timestamp submitTime, String editorName, Timestamp authDate, Integer authProgress,
			String authFeedback, Boolean isOptsHandling, Boolean isDirectorHandling, Boolean isEditorHandling,
			Boolean isAccepted, String state, String stateDeail) {
		this.id = id;
		this.realname = realname;
		this.bookname = bookname;
		this.deadline = deadline;
		this.type = type;
		this.typeName = typeName;
		this.submitTime = submitTime;
		this.editorName = editorName;
		this.authDate = authDate;
		this.authProgress = authProgress;
		this.authFeedback = authFeedback;
		this.isOptsHandling = isOptsHandling;
		this.isDirectorHandling = isDirectorHandling;
		this.isEditorHandling = isEditorHandling;
		this.isAccepted = isAccepted;
		this.state = state;
		this.stateDeail = stateDeail;
	}

	public TopicDeclarationVO() {
		super();
	}

	public Timestamp getSubmitTime1() {
		return submitTime1;
	}

	public void setSubmitTime1(Timestamp submitTime1) {
		this.submitTime1 = submitTime1;
	}

	public Timestamp getSubmitTime2() {
		return submitTime2;
	}

	public void setSubmitTime2(Timestamp submitTime2) {
		this.submitTime2 = submitTime2;
	}

	public String getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}
}
