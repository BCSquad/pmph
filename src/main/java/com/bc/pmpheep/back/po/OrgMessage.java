package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * OrgMessage（机构用户消息表） 实体类  -- org_message
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("OrgMessage")
public class OrgMessage implements Serializable {
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
	
	public OrgMessage(){
		
	}
	public OrgMessage(Long id){
		this.id=id;
	}
	/**
	 * 
	 * @param msgCode  消息标识
	 * @param msgType  消息类型     0=系统消息/1=站内群发/2=站内私信
	 */
	public OrgMessage(String msgCode, Integer msgType) {
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
		return "{id:" + id + ", msgCode:" + msgCode + ", msgType:" + msgType + "}";
	}

}
