package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.bc.pmpheep.annotation.ExcelHeader;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
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
	 * 书名
	 */
	@ExcelHeader(header = "选题名称")
	private String bookname;
	/**
	 * 提交选题申报用户姓名
	 */
	@ExcelHeader(header = "作者")
	private String submitName;
	/**
	 * 提交选题申报用户名
	 */
	@ExcelHeader(header = "作者账号")
	private String submitUser;
	/**
	 * 主编姓名
	 */
	private String realname;

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
	 * 提交日期(前台到后台的日期)
	 */
	@ExcelHeader(header = "提交日期")
	private Timestamp submitTime;

	/**
	 * 提交日期(前台到后台的日期) 查询起
	 */
	private Timestamp submitTime1;

	/**
	 * 提交日期(前台到后台的日期) 查询止
	 */
	private Timestamp submitTime2;



	/**
	 * 是否被主任退回
	 */
	private Boolean isRejectedByDirector;

	@ExcelHeader(header = "是否退回")
	private String rejectedExcel;
	/**
	 * 退回原因
	 */
	@ExcelHeader(header = "退回理由")
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

	public Boolean getIsRejectedByDirector() {
		return isRejectedByDirector;
	}

	public void setIsRejectedByDirector(Boolean isRejectedByDirector) {
		this.isRejectedByDirector = isRejectedByDirector;
		this.rejectedExcel = ObjectUtil.isNull(isRejectedByDirector)?"":(isRejectedByDirector?"已退回":"未退回");
	}

	public String getReasonDirector() {
		return reasonDirector;
	}

	public void setReasonDirector(String reasonDirector) {
		this.reasonDirector = reasonDirector;
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


	public String getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}

	public String getRejectedExcel() {
		return rejectedExcel;
	}

	public void setRejectedExcel(String rejectedExcel) {
		this.rejectedExcel = rejectedExcel;
	}
}
