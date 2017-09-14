package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * OrgUserMessage (消息-机构用户映射表（多对多）  )实体类 -- Org_User_Message
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("OrgUserMessage")
public class OrgUserMessage implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 消息id
     */
    private Long msgId;
    /**
     * 机构id
     */
    private Long userId;
    /**
     * 是否已读
     */
    private boolean isRead;
    /**
     * 是否逻辑删除
     */
    private boolean isDeleted;
    /**
     * 创建时间
     */
    private Timestamp gmtCreate;
    /**
     * 修改时间
     */
    private Timestamp gmtUpdate;
    
    public OrgUserMessage(){
    	
    }
    public OrgUserMessage(Long id){
    	this.id=id;
    }
    /**
     * 
     * @param msgId  消息id
     * @param userId  机构id
     * @param isRead  是否已读
     * @param isDeleted  是否被逻辑删除
     */
    public OrgUserMessage(Long msgId, Long userId, boolean isRead, boolean isDeleted) {
		this.msgId = msgId;
		this.userId = userId;
		this.isRead = isRead;
		this.isDeleted = isDeleted;
	}

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getMsgId() {
	return msgId;
    }

    public void setMsgId(Long msgId) {
	this.msgId = msgId;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public boolean isIsRead() {
	return isRead;
    }

    public void setIsRead(boolean isRead) {
	this.isRead = isRead;
    }

    public boolean isIsDeleted() {
	return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
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
	return "{id:" + id + ", msgId:" + msgId + ", userId:" + userId + ", isRead:" + isRead
		+ ", isDelete:" + isDeleted + ", gmtCreate:" + gmtCreate + ", gmtUpdate:" + gmtUpdate + "}";
    }

}
