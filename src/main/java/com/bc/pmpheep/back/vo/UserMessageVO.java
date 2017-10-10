package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：系统消息页面VO
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @date (开发日期) 2017年10月10日
 * @修改人 ：曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("UserMessageVO")
public class UserMessageVO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 标题
	 */
	private String titel;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 发送者id
	 */
	private Long senderId;

	/**
	 * 发送者姓名
	 */
	private String sendName;

	/**
	 * 发送时间
	 */
	private Timestamp sendTime;

	/**
	 * 消息id
	 */
	private String msgId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
