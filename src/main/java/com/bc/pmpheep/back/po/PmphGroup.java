package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * PmphGroup   后台小组表  实体类
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("PmphGroup")
public class PmphGroup implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 小组名称
     */
    private String groupName;
    /**
     * 小组头像
     */
    private String groupImage;
    /**
     * 创建人id
     */
    private Long founderId;
    /**
     * 书籍id
     */
    private Long bookId;
    /**
     * 备注
     */
    private String note;
    /**
     * 创建时间
     */
    private Timestamp gmtCreate;
    /**
     * 修改时间
     */
    private Timestamp gmtUpdate;
    /**
     * 最后一条消息的时间
     */
    private Timestamp gmtLastMessage;
    
    public PmphGroup(){
    	
    }
    
    public PmphGroup(Long id){
    	this.id=id;
    }

    public PmphGroup(String groupName, String groupImage, Long founderId, Long bookId, String note,Timestamp gmtLastMessage, Timestamp gmtCreate,
    		Timestamp gmtUpdate) {
	this.groupName = groupName;
	this.groupImage = groupImage;
	this.founderId = founderId;
	this.bookId = bookId;
	this.note = note;
	this.gmtCreate = gmtCreate;
	this.gmtUpdate = gmtUpdate;
	this.gmtLastMessage=gmtLastMessage;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getGroupName() {
	return groupName;
    }

    public void setGroupName(String groupName) {
	this.groupName = groupName;
    }

    public String getGroupImage() {
	return groupImage;
    }

    public void setGroupImage(String groupImage) {
	this.groupImage = groupImage;
    }

    public Long getFounderId() {
	return founderId;
    }

    public void setFounderId(Long founderId) {
	this.founderId = founderId;
    }

    public Long getBookId() {
	return bookId;
    }

    public void setBookId(Long bookId) {
	this.bookId = bookId;
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
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

	public Timestamp getGmtLastMessage() {
		return gmtLastMessage;
	}

	public void setGmtLastMessage(Timestamp gmtLastMessage) {
		this.gmtLastMessage = gmtLastMessage;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", groupName:" + groupName + ", groupImage:"
				+ groupImage + ", founderId:" + founderId + ", bookId:"
				+ bookId + ", note:" + note + ", gmtCreate:" + gmtCreate
				+ ", gmtUpdate:" + gmtUpdate + ", gmtLastMessage:"
				+ gmtLastMessage + "}";
	}

    

}
