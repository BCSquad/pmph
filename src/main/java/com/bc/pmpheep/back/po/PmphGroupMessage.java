package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;



import org.apache.ibatis.type.Alias;

/**
 * PmphGroupMessage(后台小组消息表) 实体类   ,对应数据库 pmph_group_message 
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
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
    private Timestamp gmtCreate;

    
    public PmphGroupMessage(Long id) {
		super();
		this.id = id;
	}

	public PmphGroupMessage() {
		super();
	}

	public PmphGroupMessage(Long groupId, Long memberId, String msgContent) {
	this.groupId = groupId;
	this.memberId = memberId;
	this.msgContent = msgContent;
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

    public Timestamp getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
	return "{id:" + id + ", groupId:" + groupId + ", memberId:" + memberId + ", msgContent:"
		+ msgContent + ", gmtCreate:" + gmtCreate + "}";
    }

}
