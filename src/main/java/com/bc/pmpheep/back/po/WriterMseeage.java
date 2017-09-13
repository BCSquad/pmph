package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * WriterMseeage 实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("WriterMseeage")
public class WriterMseeage implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 消息标识
	 */
	private String msgCode;
	/**
	 * 消息类型
	 */
	private Integer msgType;

	public WriterMseeage(String msgCode, Integer msgType) {
		this.msgCode = msgCode;
		this.msgType = msgType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	@Override
	public String toString() {
		return "WriterMseeage [id=" + id + ", msgCode=" + msgCode + ", msgType=" + msgType + "]";
	}

}
