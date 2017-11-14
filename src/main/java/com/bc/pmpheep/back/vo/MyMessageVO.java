package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.MessageAttachment;

@SuppressWarnings("serial")
@Alias("MyMessageVO")
public class MyMessageVO implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 消息id
	 */
	private String msgId;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 发送者id
	 */
	private Long senderId;
	/**
	 * 发送者类别
	 */
	private Integer senderType;
	/**
	 * 发送者头像
	 */
	private String senderAvatar;
	/**
	 * 发送者姓名
	 */
	private String senderName;
	/**
	 * 消息附件列表
	 */
	private List<MessageAttachment> messageAttachments;
	/**
	 * 发送时间
	 */
	private Timestamp sendTime;
	/**
	 * 是否已读
	 */
	private Boolean isRead;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户类型
	 */
	private Integer userType;

	private Boolean is_withdraw;

	public Boolean getIs_withdraw() {
		return is_withdraw;
	}

	public void setIs_withdraw(Boolean is_withdraw) {
		this.is_withdraw = is_withdraw;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<MessageAttachment> getMessageAttachments() {
		return messageAttachments;
	}

	public void setMessageAttachments(List<MessageAttachment> messageAttachments) {
		this.messageAttachments = messageAttachments;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Integer getSenderType() {
		return senderType;
	}

	public void setSenderType(Integer senderType) {
		this.senderType = senderType;
	}

	public String getSenderAvatar() {
		return senderAvatar;
	}

	public void setSenderAvatar(String senderAvatar) {
		this.senderAvatar = senderAvatar;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

}
