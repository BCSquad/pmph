package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * PmphGroupFile 实体类
 * 
 * @author 曾庆峰
 *
 */
@Alias("PmphGroupFile")
public class PmphGroupFile implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 小组id
     */
    private Long groupId;
    /**
     * 上传者id
     */
    private Long memberId;
    /**
     * 文件id
     */
    private String fileId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 下载次数
     */
    private Integer download;
    /**
     * 上传时间
     */
    private Date gmtCreate;

    public PmphGroupFile(Long groupId, Long memberId, String fileId, String fileName, Integer download,
	    Date gmtCreate) {
	this.groupId = groupId;
	this.memberId = memberId;
	this.fileId = fileId;
	this.fileName = fileName;
	this.download = download;
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

    public String getFileId() {
	return fileId;
    }

    public void setFileId(String fileId) {
	this.fileId = fileId;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public Integer getDownload() {
	return download;
    }

    public void setDownload(Integer download) {
	this.download = download;
    }

    public Date getGmtCreate() {
	return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
	return "PmphGroupFile [id=" + id + ", groupId=" + groupId + ", memberId=" + memberId + ", fileId=" + fileId
		+ ", fileName=" + fileName + ", download=" + download + ", gmtCreate=" + gmtCreate + "]";
    }

}
