package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：选题申报受理日志表实体
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月19日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("TopicLog")
public class TopicLog implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 选题id
	 */
	private Long topicId;
	/**
	 * 操作者id （社内）
	 */
	private Long userId;
	/**
	 * 事件
	 */
	private String topicEvent;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;


	public TopicLog() {
		super();
	}

	public TopicLog(Long topicId, Long userId, String topicEvent, Timestamp gmtCreate) {
		super();
		this.topicId = topicId;
		this.userId = userId;
		this.topicEvent = topicEvent;
		this.gmtCreate = gmtCreate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTopicEvent() {
		return topicEvent;
	}

	public void setTopicEvent(String topicEvent) {
		this.topicEvent = topicEvent;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}
