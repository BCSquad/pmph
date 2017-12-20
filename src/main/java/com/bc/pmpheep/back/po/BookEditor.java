package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年12月19日 下午4:50:46
 *
 **/
@SuppressWarnings("serial")
@Alias("BookEditor")
public class BookEditor implements Serializable {
	/**
     * 主键
     */
    private Long    id;
    /**
     * 主键
     */
    private Long    bookId;
    /**
     * 主键
     */
    private Long    authorId;
    /**
     * 主键
     */
    private Long    editorId;
    /**
     * 创建时间
     */
    private Timestamp gmtCreate;
 
    
	public BookEditor() {
		super();
	}

	public BookEditor(Long bookId, Long authorId, Long editorId) {
		super();
		this.bookId = bookId;
		this.authorId = authorId;
		this.editorId = editorId;
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


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	public Long getEditorId() {
		return editorId;
	}


	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}


	public Timestamp getGmtCreate() {
		return gmtCreate;
	}


	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}


	@Override
	public String toString() {
		return "{id:" + id + ", bookId:" + bookId + ", authorId:" + authorId
				+ ", editorId:" + editorId + ", gmtCreate:" + gmtCreate + "}";
	}

	
    
    

}
