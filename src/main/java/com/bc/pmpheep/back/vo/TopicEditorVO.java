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
	// 作家姓名
	private String realName;
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
	// 编辑是否接受办理
	private Boolean isAccepted;

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

}
