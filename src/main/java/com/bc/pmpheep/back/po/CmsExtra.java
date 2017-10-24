package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * CMS内容附件 实体
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:12:49
 *
 */
@SuppressWarnings("serial")
@Alias("CmsExtra")
public class CmsExtra implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 内容id
	 */
	private Long contentId;
	/**
	 * 附件
	 */
	private String attachment;
	/**
	 * 附件名称
	 */
	private String attachmentName;
	/**
	 * 下载次数
	 */
	private Long download;
	
	public CmsExtra() {
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

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public Long getDownload() {
		return download;
	}

	public void setDownload(Long download) {
		this.download = download;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", contentId:" + contentId + ", attachment:"
				+ attachment + ", attachmentName:" + attachmentName
				+ ", download:" + download + "}";
	}
	
	

}