package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.bc.pmpheep.annotation.ExcelHeader;
import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午4:50:46
 *
 **/
@SuppressWarnings("serial")
@Alias("BookCorrectionAuditVO")
public class BookCorrectionAuditVO implements Serializable {
	/**
     * 主键
     */
    private Long    id;
    /**
     * 图书名称
     */
	@ExcelHeader(header = " 图书名称")
    private String    bookname;
    /**
     * 图书图片
     */
    private String imageUrl;
    /**
     * 图书作者
     */
	@ExcelHeader(header = "图书作者")
    private String author;
    /**
     * 图书出版时间
     */
	//@ExcelHeader(header = "图书出版时间")
    private Date publishDate;
    /**
     * 页码
     */
	@ExcelHeader(header = "页码")
    private Integer page;
    /**
     * 行数
     */
	@ExcelHeader(header = "行数")
    private Integer line;
    /**
     * 纠错内容
     */

	@ExcelHeader(header = "纠错内容")
    private String content ;
    /**
     * 附件
     */
    private String attachment ;
    /**
     * 附件名称
     */
    private String attachmentName ;
    /**
     * 提交者姓名
     */
	@ExcelHeader(header = "提交者姓名")
    private String realname;
    /**
     * 提交时间
     */
	@ExcelHeader(header = "提交时间")
    private Timestamp gmtCreate;
    /**
     * 第一主编回复内容
     */
	@ExcelHeader(header = "第一主编回复内容")
    private String authorReply ;
    /**
     * 第一主编是否回复
     */
    private Boolean isAuthorReplied ;
    /** 
     * 策划编辑是否受理
     */
    private Boolean isEditorHandling ;
    /**
     * 策划编辑是否回复
     */
    private Boolean isEditorReplied ;
    /**
     * 策划编辑回复内容
     */
	@ExcelHeader(header = "策划编辑回复内容")
    private String  editorReply ;
    /**
     *检查结果
     */
    private Boolean result ;
    /**
     * 策划编辑姓名
     */
	//@ExcelHeader(header = "策划编辑姓名")
    private String editorName;
    
    
	public String getEditorName() {
		return editorName;
	}


	public BookCorrectionAuditVO setEditorName(String editorName) {
		this.editorName = editorName;
		return this;
	}


	public BookCorrectionAuditVO() {
		super();
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBookname() {
		return bookname;
	}


	public void setBookname(String bookname) {
		this.bookname = bookname;
	}


	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public Integer getLine() {
		return line;
	}


	public void setLine(Integer line) {
		this.line = line;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getAttachment() {
		return attachment;
	}


	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


	public String getAttachmentName() {
		return attachmentName;
	}


	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}


	public String getRealname() {
		return realname;
	}


	public void setRealname(String realname) {
		this.realname = realname;
	}


	public Timestamp getGmtCreate() {
		return gmtCreate;
	}


	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}


	public String getAuthorReply() {
		return authorReply;
	}


	public void setAuthorReply(String authorReply) {
		this.authorReply = authorReply;
	}


	public Boolean getIsEditorHandling() {
		return isEditorHandling;
	}


	public void setIsEditorHandling(Boolean isEditorHandling) {
		this.isEditorHandling = isEditorHandling;
	}


	public Boolean getIsEditorReplied() {
		return isEditorReplied;
	}


	public void setIsEditorReplied(Boolean isEditorReplied) {
		this.isEditorReplied = isEditorReplied;
	}


	public String getEditorReply() {
		return editorReply;
	}


	public void setEditorReply(String editorReply) {
		this.editorReply = editorReply;
	}


	public Boolean getResult() {
		return result;
	}


	public void setResult(Boolean result) {
		this.result = result;
	}
	

	public Boolean getIsAuthorReplied() {
		return isAuthorReplied;
	}


	public void setIsAuthorReplied(Boolean isAuthorReplied) {
		this.isAuthorReplied = isAuthorReplied;
	}



	@Override
	public String toString() {
		return "BookCorrectionAuditVO [id=" + id + ", bookname=" + bookname + ", imageUrl=" + imageUrl + ", author="
				+ author + ", publishDate=" + publishDate + ", page=" + page + ", line=" + line + ", content=" + content
				+ ", attachment=" + attachment + ", attachmentName=" + attachmentName + ", realname=" + realname
				+ ", gmtCreate=" + gmtCreate + ", authorReply=" + authorReply + ", isAuthorReplied=" + isAuthorReplied
				+ ", isEditorHandling=" + isEditorHandling + ", isEditorReplied=" + isEditorReplied + ", editorReply="
				+ editorReply + ", result=" + result + ", editorName=" + editorName + "]";
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public BookCorrectionAuditVO setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}


	public String getAuthor() {
		return author;
	}


	public BookCorrectionAuditVO setAuthor(String author) {
		this.author = author;
		return this;
	}


	public Date getPublishDate() {
		return publishDate;
	}


	public BookCorrectionAuditVO setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
		return this;
	}
    
	
   
    

}
