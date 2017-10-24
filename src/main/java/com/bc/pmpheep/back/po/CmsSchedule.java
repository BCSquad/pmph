package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

/**
 * CMS定时发布内容表（发布后删除条目）
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:13:18
 *
 */
@SuppressWarnings("serial")
@Alias("CmsSchedule")
public class CmsSchedule implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 内容id
	 */
	private Long contentId;
	/**
	 * 定时发布时间
	 */
	private Timestamp scheduledTime;
	
	public CmsSchedule() {
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
	public Timestamp getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(Timestamp scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	@Override
	public String toString() {
		return "{id:" + id + ", contentId:" + contentId + ", scheduledTime:"
				+ scheduledTime + "}";
	}
	

}