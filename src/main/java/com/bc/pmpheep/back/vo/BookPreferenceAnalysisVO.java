package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("BookPreferenceAnalysisVO")
public class BookPreferenceAnalysisVO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 图书名称
	 */
	private String bookname;
	/**
	 * 点击数
	 */
	private Long clicks;
	/**
	 * 收藏数
	 */
	private Long bookmarks;
	/**
	 * 3以上星评价次数
	 */
	private Long count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Long getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(Long bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
