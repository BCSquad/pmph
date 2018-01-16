package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：作家用户动态表实体类
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018年1月16日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@SuppressWarnings("serial")
@Alias("WriterUserTrendst")
public class WriterUserTrendst implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 用户id（writer_user）
	 */
	private Long userId;
	/**
	 * 是否所有人可见
	 */
	private Boolean isPublic;
	/**
	 * 动态类型
	 */
	private Integer type;
	/**
	 * 动态描述
	 */
	private String detail;
	/**
	 * 文章/评论主键
	 */
	private Long cmsContentId;
	/**
	 * 图书主键
	 */
	private Long bookId;
	/**
	 * 书评主键
	 */
	private Long bookCommentId;
	/**
	 * 发生时间
	 */
	private Timestamp gmtCreate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getCmsContentId() {
		return cmsContentId;
	}

	public void setCmsContentId(Long cmsContentId) {
		this.cmsContentId = cmsContentId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getBookCommentId() {
		return bookCommentId;
	}

	public void setBookCommentId(Long bookCommentId) {
		this.bookCommentId = bookCommentId;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "WriterUserTrendst [id=" + id + ", userId=" + userId + ", isPublic=" + isPublic + ", type=" + type
				+ ", detail=" + detail + ", cmsContentId=" + cmsContentId + ", bookId=" + bookId + ", bookCommentId="
				+ bookCommentId + ", gmtCreate=" + gmtCreate + "]";
	}

}
