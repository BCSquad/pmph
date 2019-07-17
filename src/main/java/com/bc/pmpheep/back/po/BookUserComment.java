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
	 * 是否长评
	 */
	private Boolean isLong;
	/**
	 * 评分
	 */
	private Double score;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 评价内容
	 */
	private String content;
	/**
	 * 是否热门
	 */
	private Boolean isHot;
	/**
	 * 热门显示顺序
	 */
	private Integer sortHot;
	/**
	 * 是否推荐
	 */
	private Boolean isPromote;
	/**
	 * 是否置顶
	 */
	private Boolean isStick;
	/**
	 * 置顶顺序
	 */
	private Integer sort;
	/**
	 * 推荐显示顺序
	 */
	private Integer sortPromote;
	/**
	 * 是否隐藏
	 */
	private Boolean isHide;
	/**
	 * 是否通过审核
	 */
	private Integer isAuth;
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
	private Boolean isDeleted;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;


	private String authReason;
	private String authReply;
	private Boolean front;

	public String getAuthReason() {
		return authReason;
	}

	public void setAuthReason(String authReason) {
		this.authReason = authReason;
	}

	public BookUserComment() {
		super();
	}

	public String getAuthReply() {
		return authReply;
	}

	public void setAuthReply(String authReply) {
		this.authReply = authReply;
	}

	public Boolean getFront() {
		return front;
	}

	public void setFront(Boolean front) {
		this.front = front;
	}

	public BookUserComment(Long bookId, Long writerId, Double score, String content, Boolean isHide, Integer isAuth,
						   Long authUserId, Timestamp authDate, Boolean isDeleted, Timestamp gmtCreate, Timestamp gmtUpdate, String authReason) {
		super();
		this.bookId = bookId;
		this.writerId = writerId;
		this.score = score;
		this.content = content;
		this.isHide = isHide;
		this.isAuth = isAuth;
		this.authUserId = authUserId;
		this.authDate = authDate;
		this.isDeleted = isDeleted;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
		this.authReason=authReason;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Integer getSortHot() {
		return sortHot;
	}

	public void setSortHot(Integer sortHot) {
		this.sortHot = sortHot;
	}

	public Boolean getIsPromote() {
		return isPromote;
	}

	public void setIsPromote(Boolean isPromote) {
		this.isPromote = isPromote;
	}

	public Integer getSortPromote() {
		return sortPromote;
	}

	public void setSortPromote(Integer sortPromote) {
		this.sortPromote = sortPromote;
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

	public Boolean getIsLong() {
		return isLong;
	}

	public void setIsLong(Boolean isLong) {
		this.isLong = isLong;
	}

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public Boolean getIsStick() {
		return isStick;
	}

	public void setIsStick(Boolean isStick) {
		this.isStick = isStick;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "bookUserComment [id=" + id + ", bookId=" + bookId + ", writerId=" + writerId + ", score=" + score
				+ ", content=" + content + ", isHide=" + isHide + ", isAuth=" + isAuth + ", authUserId=" + authUserId
				+ ", authDate=" + authDate + ", isDeleted=" + isDeleted + ", gmtCreate=" + gmtCreate + ", gmtUpdate="
				+ gmtUpdate + "]";
	}

}
