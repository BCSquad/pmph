package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

/**
 * 教材书籍变更日志表
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:32:03
 *
 */
@SuppressWarnings("serial")
@Alias("TextbookLog")
public class TextbookLog implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 教材id
	 */
	private Long textbookId;
	/**
	 * 变更者id
	 */
	private Long updaterId;
	/**
	 * 变更者是否社内用户
	 */
	private Boolean isPmphUpdater;
	/**
	 * 变更内容
	 */
	private String detail;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;

	public TextbookLog() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTextbookId() {
		return textbookId;
	}

	public void setTextbookId(Long textbookId) {
		this.textbookId = textbookId;
	}

	public Long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

	public Boolean getIsPmphUpdater() {
		return isPmphUpdater;
	}

	public void setIsPmphUpdater(Boolean isPmphUpdater) {
		this.isPmphUpdater = isPmphUpdater;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", textbookId:" + textbookId + ", updaterId:"
				+ updaterId + ", isPmphUpdater:" + isPmphUpdater + ", detail:"
				+ detail + ", gmtCreate:" + gmtCreate + "}";
	}

	
}