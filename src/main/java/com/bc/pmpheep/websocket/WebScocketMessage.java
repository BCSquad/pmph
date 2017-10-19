package com.bc.pmpheep.websocket;

import java.sql.Timestamp;

/**
 * @author MrYang
 * @CreateDate 2017年9月29日 上午11:09:43
 * 
 **/
public class WebScocketMessage {

	// 主键
	private String id;
	// 消息类型 0=系统消息/1=站内群发/2=站内私信(作家和机构用户不能群发)/3 小组互动
	private Short msgType;
	// 发送者id 0=系统/其他=用户id
	private Long senderId;
	// 发送者姓名
	private String senderName;
	// 发送者头像
	private String senderIcon;
	// 小组id
	private Long groupId;
	// 发送者类型 0=系统/1=社内用户/2=作家用户/3=机构用户
	private Short senderType;
	// 发送类型 0 新增 1 撤回 2 删除
	private Short sendType;
	// 消息标题
	private String title;
	// 消息内容
	private String content;
	// 时间
	private Timestamp time;

	public WebScocketMessage() {
		super();
	}

	public WebScocketMessage(String id, Short msgType, Long senderId, String senderName, Short senderType,
			Short sendType, String title, String content, Timestamp time) {
		super();
		this.id

				= id;
		this.msgType = msgType;
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderType = senderType;
		this.sendType = sendType;
		this.title = title;
		this.content = content;
		this.time = time;
	}

	public String getSenderIcon() {
		return senderIcon;
	}

	public void setSenderIcon(String senderIcon) {
		this.senderIcon = senderIcon;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id

				= id;
	}

	public Short getMsgType() {
		return msgType;
	}

	public void setMsgType(Short msgType) {
		this.msgType = msgType;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Short getSenderType() {
		return senderType;
	}

	public void setSenderType(Short senderType) {
		this.senderType = senderType;
	}

	public Short getSendType() {
		return sendType;
	}

	public void setSendType(Short sendType) {
		this.sendType = sendType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "{'id':" + id + ", 'msgType':" + msgType + ", 'senderId':" + senderId + ", 'senderName':'" + senderName
				+ "', 'senderIcon':'" + senderIcon + "',' groupId':" + groupId + ", 'senderType':" + senderType
				+ ", 'sendType':" + sendType + ", 'title':'" + title + "', 'content':'" + content + "', 'time':'" + time
				+ "'}";
	}

}