package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
@SuppressWarnings("serial")
@org.apache.ibatis.type.Alias("BookSource")
public class BookSource  implements Serializable {
	private Long id;
	private Long bookId;
	private String sourceName;
	private Long userId;
	private Long clicks;
	private Timestamp gmtCreate;
	private Boolean isDeleted;
	private Long authId;
	private Boolean isAuth;
	private Date authDate;
	private String fileId;

	public BookSource() {
		super();
	}

	public BookSource(Long id, Long authId, Boolean isAuth, Date authDate) {
		this.id = id;
		this.authId = authId;
		this.isAuth = isAuth;
		this.authDate = authDate;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}


	public Boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean deleted) {
		isDeleted = deleted;
	}

	public Boolean getAuth() {
		return isAuth;
	}

	public void setAuth(Boolean auth) {
		isAuth = auth;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}
