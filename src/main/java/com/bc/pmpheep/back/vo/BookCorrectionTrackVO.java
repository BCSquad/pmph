package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

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
	 * 图书版次
	 */
	@ExcelHeader(header = "版次")
    private Integer revision;
    @ExcelHeader(header = "ISBN")
    private String isbn;
	@ExcelHeader(header = "主编")
    private String author;
	/**
	 * 策划编辑名字
	 */

	@ExcelHeader(header = "策划编辑")
	private String    realname ;

	/**
	 * 责任编辑名字
	 */
	@ExcelHeader(header = "责任编辑")
	private String dutyName;

	/**
	 * 责任编辑名字
	 */
	@ExcelHeader(header = "数字编辑")
	private String digitalName;

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
	 * 提交者账号
	 */
	@ExcelHeader(header = "提交者账号")
	private String correctionName;



	/**
	 * 提交者账号
	 */
	@ExcelHeader(header = "提交时间")
	private Timestamp gmtCreate;
	/**
	 * 主编回复
	 */
	@ExcelHeader(header = "第一主编回复内容")
	private String authorReply;

	/**
	 * 检查结构（转成的字符串）
	 */
	@ExcelHeader(header = "是否勘误")
	private String   resultString ;

	/**
    /**
	 *
     * 编辑部门
     */
    private String    dpName;
    /**
     * 分类
     */

    private String    typeName ;

    /**
     * 检查结构 
     */
    private Boolean   result ;


	@ExcelHeader(header = "策划编辑回复内容")
    private String editorReply;
    /**
     * 策划编辑是否回复
     */
    private Boolean  isEditorReplied;
    /**
     * 策划编辑是否回复(转换成显示字符串)
     */

    private String   isEditorRepliedString ;


    /**
     * 图书出版时间
     */
    private Date publishDate;
    /**
     * 图书图片
     */
    private String imageUrl;
	/**
	 * 是否前台展示 (当数据库字段gmt_show 大于 0000-00-00 00:00:00 时为true)
	 */
    private Boolean showFront;


	private String showFrontStr;

	/**
	 * 最终回复时间
	 */
	private Timestamp replyDate;
    
	public Timestamp getGmtCreate() {
		return gmtCreate;
	}


	public String getDigitalName() {
		return digitalName;
	}


	public void setDigitalName(String digitalName) {
		this.digitalName = digitalName;
	}

	public Timestamp getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Timestamp replyDate) {
		this.replyDate = replyDate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}



	public Date getPublishDate() {
		return publishDate;
	}



	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public String getCorrectionName() {
		return correctionName;
	}



	public void setCorrectionName(String correctionName) {
		this.correctionName = correctionName;
	}



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
		//设置显示的检查结果
		this.resultString = null ;
		if(null != this.result && null != this.isEditorReplied && this.isEditorReplied){
			resultString = this.result?"图书勘误":"内容无误";
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
		//设置显示的检查结果
		this.resultString = null ;
		if(null != this.result && null != this.isEditorReplied && this.isEditorReplied){
			resultString = this.result?"图书勘误":"内容无误";
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

	

	public String getDutyName() {
		return dutyName;
	}



	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}


	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	@Override
	public String toString() {
		return "BookCorrectionTrackVO [id=" + id + ", bookname=" + bookname + ", dpName=" + dpName + ", typeName="
				+ typeName + ", realname=" + realname + ", dutyName=" + dutyName + ", result=" + result
				+ ", resultString=" + resultString + ", page=" + page + ", line=" + line + ", content=" + content
				+ ", authorReply=" + authorReply + ", isEditorReplied=" + isEditorReplied + ", isEditorRepliedString="
				+ isEditorRepliedString + ", author=" + author + ", correctionName=" + correctionName + "]";
	}


	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Boolean getShowFront() {
		return showFront;
	}

	public void setShowFront(Boolean showFront) {
		this.showFront = showFront;
		this.showFrontStr = showFront?"是":"否";
	}

	public String getShowFrontStr() {
		return showFrontStr;
	}

	public void setShowFrontStr(String showFrontStr) {
		this.showFrontStr = showFrontStr;
	}

	public String getEditorReply() {
		return editorReply;
	}

	public void setEditorReply(String editorReply) {
		this.editorReply = editorReply;
	}
}
