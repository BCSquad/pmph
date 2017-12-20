package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午4:50:46
 *
 **/
@SuppressWarnings("serial")
@Alias("BookCorrection")
public class BookCorrection implements Serializable {
	/**
     * 主键
     */
    private Long    id;
    /**
     * 图书id
     */
    private Long    bookId;
    /**
     * 提交纠错的用户id
     */
    private Long    userId;
    /**
     * 页码
     */
    private Integer page;
    /**
     * 行数
     */
    private Integer line;
    /**
     * 纠错内容
     */
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
     * 策划编辑是否受理
     */
    private Boolean isEditorHandling ;
    /**
     * 第一主编是否回复
     */
    private Boolean isAuthorReplied ;
    /**
     * 第一主编回复内容
     */
    private String authorReply ;
    /**
     * 策划编辑是否回复
     */
    private Boolean isEditorReplied ;
    /**
     * 策划编辑回复内容
     */
    private String  editorReply ;
    /**
     *检查结果
     */
    private Boolean result ;
    /**
     * 最终回复时间
     */
    private Date replyDate ;
    /**
     * 是否被逻辑删除
     */
    private Boolean isDeleted ;
    /**
     * 创建时间
     */
    private Timestamp gmtCreate;
    /**
     * 修改时间
     */
    private Timestamp gmtUpdate;
    
	public BookCorrection() {
		super();
	}

	public BookCorrection(Long bookId, Long userId, Integer page, Integer line,
			String content, String attachment, String attachmentName,
			Boolean isEditorHandling, Boolean isAuthorReplied,
			String authorReply, Boolean isEditorReplied, String editorReply,
			Boolean result, Date replyDate) {
		super();
		this.bookId = bookId;
		this.userId = userId;
		this.page = page;
		this.line = line;
		this.content = content;
		this.attachment = attachment;
		this.attachmentName = attachmentName;
		this.isEditorHandling = isEditorHandling;
		this.isAuthorReplied = isAuthorReplied;
		this.authorReply = authorReply;
		this.isEditorReplied = isEditorReplied;
		this.editorReply = editorReply;
		this.result = result;
		this.replyDate = replyDate;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Boolean getIsEditorHandling() {
		return isEditorHandling;
	}

	public void setIsEditorHandling(Boolean isEditorHandling) {
		this.isEditorHandling = isEditorHandling;
	}

	public Boolean getIsAuthorReplied() {
		return isAuthorReplied;
	}

	public void setIsAuthorReplied(Boolean isAuthorReplied) {
		this.isAuthorReplied = isAuthorReplied;
	}

	public String getAuthorReply() {
		return authorReply;
	}

	public void setAuthorReply(String authorReply) {
		this.authorReply = authorReply;
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

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
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

	@Override
	public String toString() {
		return "{id:" + id + ", bookId:" + bookId + ", userId:" + userId
				+ ", page:" + page + ", line:" + line + ", content:" + content
				+ ", attachment:" + attachment + ", attachmentName:"
				+ attachmentName + ", isEditorHandling:" + isEditorHandling
				+ ", isAuthorReplied:" + isAuthorReplied + ", authorReply:"
				+ authorReply + ", isEditorReplied:" + isEditorReplied
				+ ", editorReply:" + editorReply + ", result:" + result
				+ ", replyDate:" + replyDate + ", isDeleted:" + isDeleted
				+ ", gmtCreate:" + gmtCreate + ", gmtUpdate:" + gmtUpdate + "}";
	}
    
	
   
    

}
