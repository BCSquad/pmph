package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

/**
 * CMS内容收藏表（多对多）
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:17:32
 *
 */
@SuppressWarnings("serial")
@Alias("CmsUserMark")
public class CmsUserMark implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 内容id
	 */
	private Long contentId;
	/**
	 * 用户id
	 */
	private Long writerId;
	/**
	 * 收藏夹id
	 */
	private Long favoriteId;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	
	public CmsUserMark() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getContentId() {
		return contentId;
	}
	
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	
	public Long getWriterId() {
		return writerId;
	}
	
	public void setWriterId(Long writerId) {
		this.writerId = writerId;
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
		return "{id:" + id + ", contentId:" + contentId + ", writerId:"
				+ writerId + ", favoriteId:" + favoriteId + ", gmtCreate:"
				+ gmtCreate + "}";
	}

}