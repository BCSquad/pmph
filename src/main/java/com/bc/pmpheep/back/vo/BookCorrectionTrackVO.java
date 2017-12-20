package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;

/**
 * 纠错跟踪VO
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午4:50:46
 *
 **/
@SuppressWarnings("serial")
@Alias("BookCorrectionTrackVO")
public class BookCorrectionTrackVO implements Serializable {
	/**
     * 主键
     */
    private Long    id;
    /**
     * 图书名称
     */
    private String    bookname;
    /**
     * 编辑部门 
     */
    private String    dpName;
    /**
     * 分类
     */
    private String    typeName ;
    /**
     * 策划编辑名字
     */
    private String    realname ;
    /**
     * 检查结构 
     */
    private Boolean   result ;
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
     * 主编回复 
     */
    private String authorReply;
    /**
     * 策划编辑是否回复
     */
    private Boolean  isEditorReplied;
    
    public BookCorrectionTrackVO() {
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



	public String getDpName() {
		return dpName;
	}



	public void setDpName(String dpName) {
		this.dpName = dpName;
	}



	public String getTypeName() {
		return typeName;
	}



	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}



	public String getRealname() {
		return realname;
	}



	public void setRealname(String realname) {
		this.realname = realname;
	}



	public Boolean getResult() {
		return result;
	}



	public void setResult(Boolean result) {
		this.result = result;
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



	@Override
	public String toString() {
		return "{id:" + id + ", bookname:" + bookname + ", dpName:" + dpName
				+ ", typeName:" + typeName + ", realname:" + realname
				+ ", result:" + result + ", page:" + page + ", line:" + line
				+ ", content:" + content + ", authorReply:" + authorReply
				+ ", isEditorReplied:" + isEditorReplied + "}";
	}
    
	
   
    

}
