package com.bc.pmpheep.back.vo;

import com.bc.pmpheep.annotation.ExcelHeader;

import java.io.Serializable;
/**
 * BookVedioVO
 *
 * @author MrYang 2018年1月31日 上午8:53:35
 */
@SuppressWarnings("serial")
@org.apache.ibatis.type.Alias("BookVideoTrackVO")
public class BookVideoTrackVO implements Serializable {

    //主键
    private Long id;
    //图书id
    private Long bookId;
    //微视频标题
    @ExcelHeader(header = "微视频标题")
    private String title;
    //图书名称
    @ExcelHeader(header = "图书名称")
    private String bookName;
    //原始文件名称
    @ExcelHeader(header = "原始文件名称")
    private String origFileName;
    //转码后文件放置路径
    private String path;
    //上传人
    @ExcelHeader(header = "上传人")
    private String userName;
    //上传时间
    @ExcelHeader(header = "上传时间")
    private java.sql.Timestamp gmtCreate;
    //转码后文件大小
    @ExcelHeader(header = "文件大小")
    private String fileSize;
    //是否通过审核
    @ExcelHeader(header = "是否审核")
    private String isAuth;
    //1=待审核,2=未通过,3=已通过
    private Integer state;

    public BookVideoTrackVO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getOrigFileName() {
        return origFileName;
    }

    public void setOrigFileName(String origFileName) {
        this.origFileName = origFileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public java.sql.Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(java.sql.Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");
        String format = df.format(new Double(fileSize) / 1024.00 / 1024.00);
        this.fileSize = format+" M";
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Boolean isAuth) {
        this.isAuth = isAuth==null?"否":isAuth?"是":"否";
    }

    /**
     * @return the state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BookVedio2 [id=" + id + ", bookId=" + bookId + ", title=" + title + ", bookName=" + bookName + ", origFileName="
                + origFileName + ", path=" + path + ", userName=" + userName + ", gmtCreate=" + gmtCreate
                + ", fileSize=" + fileSize + ", isAuth=" + isAuth + "]";
    }

}
