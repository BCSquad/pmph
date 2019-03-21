package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
    private Long       id;

    /**
     * 标题
     */
    private String     title;

    /**
     * 消息内容
     */
    private String     content;

    /**
     * 发送者id
     */
    private Long       senderId;

    /**
     * 发送者类型 0=系统/1=社内用户/2=作家用户/3=机构用户
     */
    private int senderType;

    /**
     * 发送者姓名
     */
    private String     sendName;

    /**
     * 发送时间
     */
    private Timestamp  sendTime;

    /**
     * 消息id
     */
    private String     msgId;
    // 是否已读
    private Boolean    isRead;
    // 是否撤回 如果已读无法撤回
    private Boolean    isWithdraw;
    // 是否被逻辑删除 只有接收者可以删除
    private Boolean    isDeleted;
   private List<Long> senderIds;
    //接收者筛选类型
    private Short receiverFilterType;
    //搜索条件 仅查找人为发送的 msg_type 为 1
    private Boolean manualOnly;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    /**
     * @return the isRead
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * @param isRead the isRead to set
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * @return the isWithdraw
     */
    public Boolean getIsWithdraw() {
        return isWithdraw;
    }

    /**
     * @param isWithdraw the isWithdraw to set
     */
    public void setIsWithdraw(Boolean isWithdraw) {
        this.isWithdraw = isWithdraw;
    }

    /**
     * @return the isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the senderIds
     */
    public List<Long> getSenderIds() {
        return senderIds;
    }

    /**
     * @param senderIds the senderIds to set
     */
    public void setSenderIds(List<Long> senderIds) {
        this.senderIds = senderIds;
    }

	
	@Override
	public String toString() {
		return "UserMessageVO [id=" + id + ", title=" + title + ", content=" + content + ", senderId=" + senderId
				+ ", sendName=" + sendName + ", sendTime=" + sendTime + ", msgId=" + msgId + ", isRead=" + isRead
				+ ", isWithdraw=" + isWithdraw + ", isDeleted=" + isDeleted + ", senderIds=" + senderIds
				+ "]";
	}


    public int getSenderType() {
        return senderType;
    }

    public void setSenderType(int senderType) {
        this.senderType = senderType;
    }

    public Short getReceiverFilterType() {
        return receiverFilterType;
    }

    public void setReceiverFilterType(Short receiverFilterType) {
        this.receiverFilterType = receiverFilterType;
    }

    public Boolean getManualOnly() {
        return manualOnly;
    }

    public void setManualOnly(Boolean manualOnly) {
        this.manualOnly = manualOnly;
    }
}
