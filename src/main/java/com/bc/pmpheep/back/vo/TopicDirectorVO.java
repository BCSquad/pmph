/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

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
	// 作家姓名
	private String realName;
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

}
