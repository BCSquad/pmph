package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.annotation.ExcelHeader;

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
    @ExcelHeader(header = "图书名称")
    private String    bookname;
    /**
     * 编辑部门 
     */
    @ExcelHeader(header = "编辑部门")
    private String    dpName;
    /**
     * 分类
     */
    @ExcelHeader(header = "分类")
    private String    typeName ;
    /**
     * 策划编辑名字
     */
    @ExcelHeader(header = "策划编辑")
    private String    realname ;
    /**
     * 检查结构 
     */
    private Boolean   result ;
    /**
     * 检查结构（转成的字符串） 
     */
    @ExcelHeader(header = "检查结果")
    private String   resultString ;
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
     * 主编回复 
     */
    @ExcelHeader(header = "主编回复")
    private String authorReply;
    /**
     * 策划编辑是否回复
     */
    private Boolean  isEditorReplied;
    /**
     * 策划编辑是否回复(转换成显示字符串)
     */
    @ExcelHeader(header = "是否回复")
    private String   isEditorRepliedString ;
    
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
		if(null != result){
			resultString = result?"存在问题":"无问题";
		}
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
		if(null != isEditorReplied){
			this.isEditorRepliedString = isEditorReplied?"是":"否";
		}
		
	}

	

	public String getResultString() {
		return resultString;
	}



	public void setResultString(String resultString) {
		this.resultString = resultString;
	}



	public String getIsEditorRepliedString() {
		return isEditorRepliedString;
	}



	public void setIsEditorRepliedString(String isEditorRepliedString) {
		this.isEditorRepliedString = isEditorRepliedString;
	}



	@Override
	public String toString() {
		return "{id:" + id + ", bookname:" + bookname + ", dpName:" + dpName
				+ ", typeName:" + typeName + ", realname:" + realname
				+ ", result:" + result + ", resultString:" + resultString
				+ ", page:" + page + ", line:" + line + ", content:" + content
				+ ", authorReply:" + authorReply + ", isEditorReplied:"
				+ isEditorReplied + ", isEditorRepliedString:"
				+ isEditorRepliedString + "}";
	}
    
	
   
    

}
