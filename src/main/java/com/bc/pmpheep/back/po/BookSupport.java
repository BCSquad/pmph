package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

@Alias("BookSupport")
public class BookSupport {
	private Long id;
	private Long bookId;
	private Long supportId;

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

	public Long getSupportId() {
		return supportId;
	}

	public void setSupportId(Long supportId) {
		this.supportId = supportId;
	}

}
