package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * CMS内容-类别关联 实体
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:09:16
 *
 */
@SuppressWarnings("serial")
@Alias("CmsContentCategory")
public class CmsContentCategory implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 内容id
	 */
	private Long contentId;
	/**
	 * 内容类型
	 */
	private Long categoryId;
	
	public CmsContentCategory() {
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", contentId:" + contentId + ", categoryId:"
				+ categoryId + "}";
	}
	
	

	
}