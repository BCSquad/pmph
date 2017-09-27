package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

/**
 * 
 * UserMessage (机构信息表)实体类  -- UserMessage
 *
 * @author Mryang
 *
 * @createDate 2017年9月27日 上午11:49:16
 *
 */
@SuppressWarnings("serial")
@Alias("UserMessage")
public class UserMessage implements java.io.Serializable {
	//主键
	private Long id;
	//消息id  创建消息时先创建MongoDB条目拿到id
	private String msgId;
	//消息类型   0=系统消息/1=站内群发/2=站内私信(作家和机构用户不能群发)
	private Short msgType;
	//发送者id 0=系统/其他=用户id
	private Long senderId;
	//发送者类型  0=系统/1=社内用户/2=作家用户/3=机构用户
	private Short senderType;
	//接收者id
	private Long receiverId;
	//接收者类型  1=社内用户/2=作家/3=机构用户
	private Short receiverType;
	//是否已读  
	private Boolean isRead;
	//是否撤回  如果已读无法撤回
	private Boolean isWithdraw;
	//是否被逻辑删除  只有接收者可以删除
	private Boolean isDeleted;
	//创建时间
	private Timestamp gmtCreate;
	//修改时间
	private Timestamp gmtUpdate;
	public UserMessage() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
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
	public Short getSenderType() {
		return senderType;
	}
	public void setSenderType(Short senderType) {
		this.senderType = senderType;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public Short getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(Short receiverType) {
		this.receiverType = receiverType;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public Boolean getIsWithdraw() {
		return isWithdraw;
	}
	public void setIsWithdraw(Boolean isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Timestamp getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Timestamp getGmtUpdate() {
		return gmtUpdate;
	}
	public void setGmtUpdate(Timestamp gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}
	@Override
	public String toString() {
		return "{id:" + id + ", msgId:" + msgId + ", msgType:" + msgType
				+ ", senderId:" + senderId + ", senderType:" + senderType
				+ ", receiverId:" + receiverId + ", receiverType:"
				+ receiverType + ", isRead:" + isRead + ", isWithdraw:"
				+ isWithdraw + ", isDeleted:" + isDeleted + ", gmtCreate:"
				+ gmtCreate + ", gmtUpdate:" + gmtUpdate + "}";
	}

	
	
}