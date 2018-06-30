package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("TopicSimilarBook")
public class TopicSimilarBook implements Serializable {
	private Long id;
	private Long topicId;
	private String bookname;
	private String edition;
	private String author;
	private String booksize;
	private String publisher;
	private String printNumber;
	private String price;
	private Date publishDate;

	public TopicSimilarBook() {
		super();
	}

	public TopicSimilarBook(Long topicId, String bookname, String edition, String author, String booksize,
			String publisher, String printNumber, String price, Date publishDate) {
		super();
		this.topicId = topicId;
		this.bookname = bookname;
		this.edition = edition;
		this.author = author;
		this.booksize = booksize;
		this.publisher = publisher;
		this.printNumber = printNumber;
		this.price = price;
		this.publishDate = publishDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBooksize() {
		return booksize;
	}

	public void setBooksize(String booksize) {
		this.booksize = booksize;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPrintNumber() {
		return printNumber;
	}

	public void setPrintNumber(String printNumber) {
		this.printNumber = printNumber;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

}
