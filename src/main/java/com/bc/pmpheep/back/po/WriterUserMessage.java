package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * WriterUserMessage 实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("WriterUserMessage")
public class WriterUserMessage implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 消息id
     */
    private Long msgId;
    /**
     * 作家用户id
     */
    private Long userId;
    /**
     * 是否已读
     */
    private boolean isRead;
    /**
     * 是否被逻辑删除
     */
    private boolean isDelete;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtUpdate;

    public WriterUserMessage(Long msgId, Long userId, boolean isRead, boolean isDelete, Date gmtCreate,
	    Date gmtUpdate) {
	this.msgId = msgId;
	this.userId = userId;
	this.isRead = isRead;
	this.isDelete = isDelete;
	this.gmtCreate = gmtCreate;
	this.gmtUpdate = gmtUpdate;
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

    public boolean isIsDelete() {
	return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
	this.isDelete = isDelete;
    }

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
	return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
	this.gmtUpdate = gmtUpdate;
    }

    @Override
    public String toString() {
	return "WriterUserMessage [id=" + id + ", msgId=" + msgId + ", userId=" + userId + ", isRead=" + isRead
		+ ", isDelete=" + isDelete + ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
    }

}
