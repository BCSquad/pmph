package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.po.BookVedio;


/**
 *@author MrYang 
 *@CreateDate 2018年2月6日 下午2:59:08
 *
 **/
@SuppressWarnings("serial")
@Alias("BookVedioVO")
public class BookVedioVO implements Serializable{
	/**
	 * 主键
	 */
	private Long bookId;
	/**
	 * 图书名称
	 */
	private String bookname;
	/**
	 * 序号
	 */
	private String sn;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 版次
	 */
	private Integer revision;
	/**
	 * 图像地址
	 */
	private String imageUrl;
	/**
	 * 关联的微视频
	 */
	private List<BookVedio> bookVedios;
	public Long getBookId() {
		return bookId;
	}
	public BookVedioVO setBookId(Long bookId) {
		this.bookId = bookId;
		return this;
	}
	public String getBookname() {
		return bookname;
	}
	public BookVedioVO setBookname(String bookname) {
		this.bookname = bookname;
		return this;
	}
	public String getSn() {
		return sn;
	}
	public BookVedioVO setSn(String sn) {
		this.sn = sn;
		return this;
	}
	public String getAuthor() {
		return author;
	}
	public BookVedioVO setAuthor(String author) {
		this.author = author;
		return this;
	}
	public Integer getRevision() {
		return revision;
	}
	public BookVedioVO setRevision(Integer revision) {
		this.revision = revision;
		return this;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public BookVedioVO setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}
	public List<BookVedio> getBookVedios() {
		return bookVedios;
	}
	public BookVedioVO setBookVedios(List<BookVedio> bookVedios) {
		this.bookVedios = bookVedios;
		return this;
	}
	public BookVedioVO() {
		super();
	}
	@Override
	public String toString() {
		return "{id:" + bookId + ",bookname:" + bookname + ",sn:" + sn + ",author:"
				+ author + ",revision:" + revision + ",imageUrl:" + imageUrl
				+ ",bookVedios:" + bookVedios + "}";
	}
	
	
}
