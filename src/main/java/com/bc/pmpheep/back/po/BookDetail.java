package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述： 图书详情表实体类
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @date (开发日期) 2017年10月24日
 * @修改人 ：曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("BookDetail")
public class BookDetail implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 图书id
	 */
	private Long bookId;
	/**
	 * 图书详情
	 */
	private String detail;

	public BookDetail() {
		super();
	}

	public BookDetail(Long bookId, String detail) {
		super();
		this.bookId = bookId;
		this.detail = detail;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "bookDetail [id=" + id + ", bookId=" + bookId + ", detail=" + detail + "]";
	}

}
