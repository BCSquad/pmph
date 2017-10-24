package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

/**
 * CMS内容点赞表（多对多）
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:15:40
 *
 */
@SuppressWarnings("serial")
@Alias("CmsUserLike")
public class CmsUserLike implements java.io.Serializable {

	// Fields
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
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	
	public CmsUserLike() {
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

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", contentId:" + contentId + ", writerId:"
				+ writerId + ", gmtCreate:" + gmtCreate + "}";
	}

	

}