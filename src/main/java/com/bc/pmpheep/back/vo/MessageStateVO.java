package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * @author MrYang
 * @CreateDate 2017年9月27日 下午2:24:38
 * 
 **/
@SuppressWarnings("serial")
@Alias("MessageStateVO")
public class MessageStateVO implements Serializable {
	// 主键
	private Long id;
	// 消息id 创建消息时先创建MongoDB条目拿到id
	private String msgId;
	// 发送者id 0=系统/其他=用户id
	private Long senderId;
	// 是否已读
	private Boolean isRead;
	// 消息类型 0=系统消息/1=站内群发/2=站内私信(作家和机构用户不能群发)
	private Short msgType;
	// 发送时间
	private Timestamp sendTime;
	/**
	 * 教材id
	 */
	private Long materialId;
	// 接收时间
	private Timestamp reciveTime;
	// 接收人
	private String name;
	//接收人的账号
	private String username;
	// 接收人单位
	private String dptname;
	// 接收人电话
	private String handphone;
	// 接收者类型 1=社内用户/2=作家/3=机构用户
	private Short receiverType;
	// 是否撤回 如果已读无法撤回
	private Boolean isWithdraw;
	// 条件分页总条数分页查询
	private Integer count;

	public MessageStateVO() {
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

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Timestamp getReciveTime() {
		return reciveTime;
	}

	public void setReciveTime(Timestamp reciveTime) {
		this.reciveTime = reciveTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDptname() {
		return dptname;
	}

	public void setDptname(String dptname) {
		this.dptname = dptname;
	}

	public String getHandphone() {
		return handphone;
	}

	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}

	public Short getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(Short receiverType) {
		this.receiverType = receiverType;
	}

	public Boolean getIsWithdraw() {
		return isWithdraw;
	}

	public void setIsWithdraw(Boolean isWithdraw) {
		this.isWithdraw = isWithdraw;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", msgId:" + msgId + ", senderId:" + senderId + ", isRead:" + isRead + ", msgType:"
				+ msgType + ", sendTime:" + sendTime + ", reciveTime:" + reciveTime + ", name:" + name + ", dptname:"
				+ dptname + ", handphone:" + handphone + ", receiverType:" + receiverType + ", isWithdraw:" + isWithdraw
				+ ", count:" + count + "}";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
