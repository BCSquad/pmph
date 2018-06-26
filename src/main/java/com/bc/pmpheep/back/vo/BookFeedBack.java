package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;
import org.apache.ibatis.type.Alias;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午4:50:46
 *
 **/
@SuppressWarnings("serial")
@Alias("BookFeedBack")
public class BookFeedBack implements Serializable {
	/**
     * 主键
     */

    private Long    id;
	/**
	 * 图书名字
	 */
	@ExcelHeader(header = "书名")
	private String bookname;

	/**
	 * isbn
	 */
	@ExcelHeader(header = "isbn")
	private String isbn;

	/**
	 * 反馈人
	 */
	@ExcelHeader(header = "反馈人")
	private String feedBackName;

	/**
	 * 审核人
	 */
	@ExcelHeader(header = "审核人")
	private String authorname;

	/**
	 * 审核状态
	 */

	private Integer result;
	/**
	 * 审核状态
	 */
	@Transient
	@ExcelHeader(header = "审核状态")
	private String resultS;
    /**
     * 图书id
     */
    private Long    bookId;
    /**
     * 提交反馈的用户id
     */
    private Long userId;
    /**
     * 读者反馈内容
     */
	@ExcelHeader(header = "反馈内容")
    private String content;
    /**
     * 是否已审核
     */
    private Integer isAuth;
    /**
     * 审核人id
     */
    private Integer authorId;
    /**
     * 回复内容
     */
	@ExcelHeader(header = " 回复内容")
    private String authorReply ;
    /**
     * 回复时间
     */

    private Date authDate ;
	@ExcelHeader(header = "审核日期")
	private String authDateS ;
    /**
     * 是否已逻辑删除
     */
    private String isDeleted ;
    /**
     * 提交时间
     */
	@ExcelHeader(header = "提交时间")
    private Timestamp gmtCreate;

	/**
	 * 数据更新时间
	 */
	private Timestamp gmt_update;


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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorReply() {
		return authorReply;
	}

	public void setAuthorReply(String authorReply) {
		this.authorReply = authorReply;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmt_update() {
		return gmt_update;
	}

	public void setGmt_update(Timestamp gmt_update) {
		this.gmt_update = gmt_update;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getFeedBackName() {
		return feedBackName;
	}

	public void setFeedBackName(String feedBackName) {
		this.feedBackName = feedBackName;
	}

	public String getAuthorname() {
		return authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getResultS() {
		return resultS;
	}

	public void setResultS(String resultS) {
		this.resultS = resultS;
	}

	public String getAuthDateS() {
		return authDateS;
	}

	public void setAuthDateS(String authDateS) {
		this.authDateS = authDateS;
	}
}
