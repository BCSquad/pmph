package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：图书收藏表实体类
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
@Alias("bookUserMark")
public class bookUserMark implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 图书id
	 */
	private Long bookId;
	/**
	 * 用户id
	 */
	private Long WriterId;
	/**
	 * 收藏夹id
	 */
	private Long favoriteId;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;

	public bookUserMark() {
		super();
	}

	public bookUserMark(Long bookId, Long writerId, Long favoriteId, Timestamp gmtCreate) {
		super();
		this.bookId = bookId;
		WriterId = writerId;
		this.favoriteId = favoriteId;
		this.gmtCreate = gmtCreate;
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

	public Long getWriterId() {
		return WriterId;
	}

	public void setWriterId(Long writerId) {
		WriterId = writerId;
	}

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "bookUserMark [id=" + id + ", bookId=" + bookId + ", WriterId=" + WriterId + ", favoriteId=" + favoriteId
				+ ", gmtCreate=" + gmtCreate + "]";
	}

}
