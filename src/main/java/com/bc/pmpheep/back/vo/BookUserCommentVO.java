package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述： 图书评论VO
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年10月27日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("BookUserCommentVO")
public class BookUserCommentVO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 图书id
	 */
	private Long bookId;
	/**
	 * 是否长评
	 */
	private Boolean isLong;
	/**
	 * 书籍名称
	 */
	private String bookname;
	/**
	 * isbn
	 */
	private String isbn;
	/**
	 * isbn/书籍名称
	 */
	private String name;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 用户id
	 */
	private Long writerId;
	/**
	 * 用户名称
	 */
	private String writerName;
	/**
	 * 评分
	 */
	private Double score;
	/**
	 * 评价内容
	 */
	private String content;
	/**
	 * 是否热门
	 */
	private Boolean isHot;

	/**
	 * 是否置顶
	 */
	private Boolean isStick;
	/**
	 * 热门显示顺序
	 */
	private Integer sortHot;
	/**
	 * 是否推荐
	 */
	private Boolean isPromote;
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
	 * 审核通过时间
	 */
	private Timestamp authDate;
	/**
	 * 是否逻辑删除
	 */
	private Boolean isDeleted;
	/**
	 * 评论时间
	 */
	private Timestamp gmtCreate;

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

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getWriterId() {
		return writerId;
	}

	public void setWriterId(Long writerId) {
		this.writerId = writerId;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
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

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public Boolean getIsStick() {
		return isStick;
	}

	public void setIsStick(Boolean isStick) {
		this.isStick = isStick;
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

	public Boolean getIsLong() {
		return isLong;
	}

	public void setIsLong(Boolean isLong) {
		this.isLong = isLong;
	}

}
