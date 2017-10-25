package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：图书评论表实体类
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
@Alias("BookUserComment")
public class BookUserComment implements Serializable {
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
	private Long writerId;
	/**
	 * 评分
	 */
	private Double score;
	/**
	 * 评价内容
	 */
	private String content;
	/**
	 * 是否隐藏
	 */
	private Boolean isHide;
	/**
	 * 是否通过审核
	 */
	private Boolean isAuth;
	/**
	 * 审核者id
	 */
	private Long authUserId;
	/**
	 * 审核通过时间
	 */
	private Timestamp authDate;
	/**
	 * 是否逻辑删除
	 */
	private Boolean isDelete;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;

	public BookUserComment() {
		super();
	}

	public BookUserComment(Long bookId, Long writerId, Double score, String content, Boolean isHide, Boolean isAuth,
			Long authUserId, Timestamp authDate, Boolean isDelete, Timestamp gmtCreate, Timestamp gmtUpdate) {
		super();
		this.bookId = bookId;
		this.writerId = writerId;
		this.score = score;
		this.content = content;
		this.isHide = isHide;
		this.isAuth = isAuth;
		this.authUserId = authUserId;
		this.authDate = authDate;
		this.isDelete = isDelete;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
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
		return writerId;
	}

	public void setWriterId(Long writerId) {
		this.writerId = writerId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsHide() {
		return isHide;
	}

	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}

	public Boolean getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Boolean isAuth) {
		this.isAuth = isAuth;
	}

	public Long getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(Long authUserId) {
		this.authUserId = authUserId;
	}

	public Timestamp getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Timestamp authDate) {
		this.authDate = authDate;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Timestamp gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	@Override
	public String toString() {
		return "bookUserComment [id=" + id + ", bookId=" + bookId + ", writerId=" + writerId + ", score=" + score
				+ ", content=" + content + ", isHide=" + isHide + ", isAuth=" + isAuth + ", authUserId=" + authUserId
				+ ", authDate=" + authDate + ", isDelete=" + isDelete + ", gmtCreate=" + gmtCreate + ", gmtUpdate="
				+ gmtUpdate + "]";
	}

}
