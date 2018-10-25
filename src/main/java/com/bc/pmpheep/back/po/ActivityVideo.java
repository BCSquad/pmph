package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
@SuppressWarnings("serial")
@Alias("ActivityVideo")
public class ActivityVideo implements Serializable {
    //活动视频id
    private Long id ;
    //活动视频标题
    private String title;
    //原始视频存放路径
    private String origPath;
    //原始视频名称
    private String origFileName;
    //原始视频大小
    private Long origFileSize;
    //转码后存放路径
    private String path;
    //转码后视频名称
    private String fileName;
    //转码后视频大小
    private Long fileSize;
    //排序
    private Integer sort;
    //播放次数
    private Long clicks;
    //上传人
    private Long userId;
    //是否通过审核
    private Boolean isAuth;
    //审核者id
    private Long authUserId;
    //审核时间
    private Date authDate;
    //是否逻辑删除
    private Boolean isDeleted;
    //创建时间
    private Timestamp gmtCreate;
    //修改时间
    private Timestamp gmtUpdate;
    //封面图id
    private String cover;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigPath() {
        return origPath;
    }

    public void setOrigPath(String origPath) {
        this.origPath = origPath;
    }

    public String getOrigFileName() {
        return origFileName;
    }

    public void setOrigFileName(String origFileName) {
        this.origFileName = origFileName;
    }

    public Long getOrigFileSize() {
        return origFileSize;
    }

    public void setOrigFileSize(Long origFileSize) {
        this.origFileSize = origFileSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth;
    }

    public Long getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(Long authUserId) {
        this.authUserId = authUserId;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
