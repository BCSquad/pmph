package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Alias("ActivitySourceVO")
public class ActivitySourceVO implements Serializable {

    //活动资源id
    private Long id;
    //资源名称
    private String sourceName;
    //上传者id
    private Long uploaderId;
    //上传时间
    private Date gmtUpload;
    //是否逻辑删除
    private Integer isDeleted;
    //排序
    private Integer sort;
    //资源文件id
    private String fileId;
    private String realname;
    private Integer   count;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public Date getGmtUpload() {
        return gmtUpload;
    }

    public void setGmtUpload(Date gmtUpload) {
        this.gmtUpload = gmtUpload;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
