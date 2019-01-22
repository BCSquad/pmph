package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.Date;

@Alias("BookSourceVO")
public class BookSourceVO {
    private Long id;
    private Long bookId;
    private String bookName;
    private String sourceName;
    private Long userId;
    private Long clicks;
    private String userName;
    private Timestamp gmtCreate;
    private Boolean isDeleted;
    private Boolean isAuth;
    private Date authDate;
    private String fileId;
    //1=待审核,2=未通过,3=已通过
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        isDeleted = isDeleted;
    }

    public Boolean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Boolean isAuth) {
        isAuth = isAuth;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
