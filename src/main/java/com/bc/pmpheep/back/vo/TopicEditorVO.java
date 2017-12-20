/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * <p>Title:TopicEditorVO<p>
 * <p>Description:TODO<p>
 * @author Administrator
 * @date 2017年12月20日 下午4:37:58
 */
@SuppressWarnings("serial")
@Alias("TopicEditorVO")
public class TopicEditorVO implements Serializable{

	//主键
	private Long id;
	//编辑Id
	private Long userId;
	//作家姓名
	private String realName;
	//选题名称
	private String bookName;
	//预计交稿时间
	private Timestamp deadline;
	//图书类别：0=专著，1=基础理论，2=论文集，3=科普，4=应用技术，5=工具书，6=其他
	private Integer type;
	//提交时间
	private Timestamp submitTime;
	
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
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
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
	
}
