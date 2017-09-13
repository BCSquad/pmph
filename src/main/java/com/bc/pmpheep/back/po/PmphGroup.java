package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.ibatis.type.Alias;

/**
 * PmphGroup 实体类
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
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtUpdate;

    public PmphGroup(String groupName, String groupImage, Long founderId, Long bookId, String note, Date gmtCreate,
	    Date gmtUpdate) {
	this.groupName = groupName;
	this.groupImage = groupImage;
	this.founderId = founderId;
	this.bookId = bookId;
	this.note = note;
	this.gmtCreate = gmtCreate;
	this.gmtUpdate = gmtUpdate;
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
	return "{id:" + id + ", groupName:" + groupName + ", groupImage:" + groupImage + ", founderId:" + founderId
		+ ", bookId:" + bookId + ", note:" + note + ", gmtCreate:" + gmtCreate + ", gmtUpdate:" + gmtUpdate
		+ "}";
    }

}
