/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * <p>
 * Title:后台选题申报编辑界面
 * <p>
 * <p>
 * Description:受理审核选题
 * <p>
 * 
 * @author lyc
 * @date 2017年12月20日 下午4:37:58
 */
@SuppressWarnings("serial")
@Alias("TopicEditorVO")
public class TopicEditorVO implements Serializable {

	// 主键
	private Long id;
	// 编辑Id
	private Long userId;
	/**
	 * 主编姓名
	 */
	private String realName;
	/**
	 * 提交者姓名
	 */
	private String submitName;
	// 审核人姓名
	private String editorName;
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
	// 编辑是否接受办理
	private Boolean isAccepted;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String editorName) {
		this.editorName = editorName;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getSubmitName() {
		return submitName;
	}

	public void setSubmitName(String submitName) {
		this.submitName = submitName;
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
