package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：运维人员转发部门VO
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月20日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("TopicOPtsManagerVO")
public class TopicOPtsManagerVO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 作家姓名
	 */
	private String realname;
	/**
	 * 书名
	 */
	private String bookname;
	/**
	 * 预计交稿时间
	 */
	private Timestamp deadline;
	/**
	 * 图书类别 0=专著/1=基础理论/2=论文集/3=科普/4=应用技术/5=工具书/6=其他
	 */
	private Integer type;
	/**
	 * 图书类别
	 */
	private String typeName;
	/**
	 * 提交日期(前台到后台的日期)
	 */
	private Timestamp submitTime;
	/**
	 * 是否被主任退回
	 */
	private Boolean isRejectedByDirector;
	/**
	 * 退回原因
	 */
	private String reasonDirector;
	/**
	 * 选题申报状态
	 */
	private String state;
	/**
	 * 详细状态
	 */
	private String stateDeail;
	/**
	 * 审核进度
	 */
	private Integer authProgress;
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

	public Integer getAuthProgress() {
		return authProgress;
	}

	public void setAuthProgress(Integer authProgress) {
		this.authProgress = authProgress;
	}

	public Boolean getIsOptsHandling() {
		return isOptsHandling;
	}

	public void setIsOptsHandling(Boolean isOptsHandling) {
		this.isOptsHandling = isOptsHandling;
	}

	public Boolean getIsEditorHandling() {
		return isEditorHandling;
	}

	public void setIsEditorHandling(Boolean isEditorHandling) {
		this.isEditorHandling = isEditorHandling;
	}

	public Boolean getIsDirectorHandling() {
		return isDirectorHandling;
	}

	public void setIsDirectorHandling(Boolean isDirectorHandling) {
		this.isDirectorHandling = isDirectorHandling;
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

	public Boolean getIsRejectedByDirector() {
		return isRejectedByDirector;
	}

	public void setIsRejectedByDirector(Boolean isRejectedByDirector) {
		this.isRejectedByDirector = isRejectedByDirector;
	}

	public String getReasonDirector() {
		return reasonDirector;
	}

	public void setReasonDirector(String reasonDirector) {
		this.reasonDirector = reasonDirector;
	}

}
