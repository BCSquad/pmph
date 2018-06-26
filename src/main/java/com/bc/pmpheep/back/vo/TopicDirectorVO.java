/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * <p>
 * Title:后台选题申报主任界面
 * <p>
 * <p>
 * Description:受理选题分配策划编辑
 * <p>
 * 
 * @author lyc
 * @date 2017年12月20日 下午3:45:48
 */

@SuppressWarnings("serial")
@Alias("TopicDirectorVO")
public class TopicDirectorVO implements Serializable {

	// 主键
	private Long id;
	// 部门id
	private Long departmentId;
	/**
	 * 提交选题申报用户姓名
	 */
	private String submitName;
	/**
	 * 主编姓名
	 */
	private String realname;
	// 选题名称
	private String bookname;
	// 预计交稿时间
	private Timestamp deadline;
	// 图书类别：0=专著，1=基础理论，2=论文集，3=科普，4=应用技术，5=工具书，6=其他
	private Integer type;
	// 图书类别
	private String typeName;
	// 提交时间
	private Timestamp submitTime;
	// 提交时间 查询起
	private Timestamp submitTime1;
	// 提交时间 查询止
	private Timestamp submitTime2;
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

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
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

	/**
	 * 是否被编辑退回
	 */
	private Boolean isRejectedByEditor;
	/**
	 * 编辑退回原因
	 */
	private String reasonEditor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getSubmitName() {
		return submitName;
	}

	public void setSubmitName(String submitName) {
		this.submitName = submitName;
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

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public Boolean getIsRejectedByEditor() {
		return isRejectedByEditor;
	}

	public void setIsRejectedByEditor(Boolean isRejectedByEditor) {
		this.isRejectedByEditor = isRejectedByEditor;
	}

	public String getReasonEditor() {
		return reasonEditor;
	}

	public void setReasonEditor(String reasonEditor) {
		this.reasonEditor = reasonEditor;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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

}
