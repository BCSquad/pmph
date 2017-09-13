package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * PmphGroupMember（后台小组成员表） 实体类, 对应数据库   pmph_group_member 表
 * 
 * @author 曾庆峰
 *
 */
@SuppressWarnings("serial")
@Alias("PmphGroupMember")
public class PmphGroupMember implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 小组id
     */
    private Long gruopId;
    /**
     * 成员id
     */
    private Long memberId;
    /**
     * 是否作家用户
     */
    private boolean isWriter;
    /**
     * 是否创建者
     */
    private boolean isFounder;
    /**
     * 是否管理员
     */
    private boolean isAdmin;
    /**
     * 小组内显示名称
     */
    private String displayName;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtUpdate;

    public PmphGroupMember(Long gruopId, Long memberId, boolean isWriter, boolean isFounder, boolean isAdmin,
	    String displayName, Date gmtCreate, Date gmtUpdate) {
	this.gruopId = gruopId;
	this.memberId = memberId;
	this.isWriter = isWriter;
	this.isFounder = isFounder;
	this.isAdmin = isAdmin;
	this.displayName = displayName;
	this.gmtCreate = gmtCreate;
	this.gmtUpdate = gmtUpdate;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getGruopId() {
	return gruopId;
    }

    public void setGruopId(Long gruopId) {
	this.gruopId = gruopId;
    }

    public Long getMemberId() {
	return memberId;
    }

    public void setMemberId(Long memberId) {
	this.memberId = memberId;
    }

    public boolean isIsWriter() {
	return isWriter;
    }

    public void setIsWriter(boolean isWriter) {
	this.isWriter = isWriter;
    }

    public boolean isIsFounder() {
	return isFounder;
    }

    public void setIsFounder(boolean isFounder) {
	this.isFounder = isFounder;
    }

    public boolean isIsAdmin() {
	return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
	this.isAdmin = isAdmin;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
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
	return "{id:" + id + ", gruopId:" + gruopId + ", memberId:" + memberId + ", isWriter:"
		+ isWriter + ", isFounder:" + isFounder + ", isAdmin:" + isAdmin + ", displayName:" + displayName
		+ ", gmtCreate:" + gmtCreate + ", gmtUpdate:" + gmtUpdate + "}";
    }

}
