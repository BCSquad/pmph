package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * PmphGroupMessage 实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("PmphGroupMessage")
public class PmphGroupMessage implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 小组id
     */
    private Long groupId;
    /**
     * 发送者id
     */
    private Long memberId;
    /**
     * 消息内容
     */
    private String msgContent;
    /**
     * 创建时间
     */
    private Date gmtCreate;

    public PmphGroupMessage(Long groupId, Long memberId, String msgContent, Date gmtCreate) {
	this.groupId = groupId;
	this.memberId = memberId;
	this.msgContent = msgContent;
	this.gmtCreate = gmtCreate;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getGroupId() {
	return groupId;
    }

    public void setGroupId(Long groupId) {
	this.groupId = groupId;
    }

    public Long getMemberId() {
	return memberId;
    }

    public void setMemberId(Long memberId) {
	this.memberId = memberId;
    }

    public String getMsgContent() {
	return msgContent;
    }

    public void setMsgContent(String msgContent) {
	this.msgContent = msgContent;
    }

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
	return "PmphGroupMessage [id=" + id + ", groupId=" + groupId + ", memberId=" + memberId + ", msgContent="
		+ msgContent + ", gmtCreate=" + gmtCreate + "]";
    }

}
