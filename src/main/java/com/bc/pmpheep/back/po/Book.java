package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：出版图书表实体类
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @date (开发日期) 2017年10月24日
 * @修改人 ：曾庆峰
 *
 */
@Alias("Book")
public class Book implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 本版号
	 */
	private String vn;
	/**
	 * 图书名称
	 */
	private String bookname;
	/**
	 * ISBN号
	 */
	private String isbn;
	/**
	 * 序号
	 */
	private String sn;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 出版社
	 */
	private String publisher;
	/**
	 * 语言
	 */
	private String lang;
	/**
	 * 版次
	 */
	private Integer revision;
	/**
	 * 类别
	 */
	private Long type;
	/**
	 * 出版日期
	 */
	private Date publishDate;
	/**
	 * 读者对象
	 */
	private String reader;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 评分
	 */
	private Double score;
	/**
	 * 购买地址
	 */
	private String buyUrl;
	/**
	 * 图像地址
	 */
	private String imageUrl;
	/**
	 * PDF地址
	 */
	private String pdfUrl;
	/**
	 * 点击数
	 */
	private Long clicks;
	/**
	 * 评论数
	 */
	private Long comments;
	/**
	 * 点赞数
	 */
	private Long likes;
	/**
	 * 收藏数
	 */
	private Long bookmarks;
	/**
	 * 是否分类置顶
	 */
	private Boolean isStick;
	/**
	 * 分类显示顺序
	 */
	private Integer sort;
	/**
	 * 置顶到期时间
	 */
	private Date deadlineStick;
	/**
	 * 是否新书
	 */
	private Boolean isNew;
	/**
	 * 新书显示顺序
	 */
	private Integer sortNew;
	/**
	 * 新书到期时间
	 */
	private Date deadlineNew;
	/**
	 * 是否推荐
	 */
	private Boolean isPromote;
	/**
	 * 推荐显示顺序
	 */
	private Integer sortPromote;
	/**
	 * 推荐到期时间
	 */
	private Date deadlinePromote;
	/**
	 * 销量
	 */
	private Long sales;
	/**
	 * 图书是否上架
	 */
	private Boolean isOnSale;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;
	/**
	 * 书籍简介
	 */
	private String content;

	public Book() {
		super();
	}

	public Book(String vn, String bookname, String isbn, String sn, String author, String publisher, String lang,
			Integer revision, Long type, Date publishDate, String reader, Double price, Double score, String buyUrl,
			String imageUrl, String pdfUrl, Long clicks, Long comments, Long likes, Long bookmarks, Boolean isStick,
			Integer sort, Date deadlineStick, Boolean isNew, Integer sortNew, Date deadlineNew, Boolean isPromote,
			Integer sortPromote, Date deadlinePromote, Long sales, Boolean isOnSale, Timestamp gmtCreate,
			Timestamp gmtUpdate, String content) {
		super();
		this.vn = vn;
		this.bookname = bookname;
		this.isbn = isbn;
		this.sn = sn;
		this.author = author;
		this.publisher = publisher;
		this.lang = lang;
		this.revision = revision;
		this.type = type;
		this.publishDate = publishDate;
		this.reader = reader;
		this.price = price;
		this.score = score;
		this.buyUrl = buyUrl;
		this.imageUrl = imageUrl;
		this.pdfUrl = pdfUrl;
		this.clicks = clicks;
		this.comments = comments;
		this.likes = likes;
		this.bookmarks = bookmarks;
		this.isStick = isStick;
		this.sort = sort;
		this.deadlineStick = deadlineStick;
		this.isNew = isNew;
		this.sortNew = sortNew;
		this.deadlineNew = deadlineNew;
		this.isPromote = isPromote;
		this.sortPromote = sortPromote;
		this.deadlinePromote = deadlinePromote;
		this.sales = sales;
		this.isOnSale = isOnSale;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getDeadlineStick() {
		return deadlineStick;
	}

	public void setDeadlineStick(Date deadlineStick) {
		this.deadlineStick = deadlineStick;
	}

	public Date getDeadlineNew() {
		return deadlineNew;
	}

	public void setDeadlineNew(Date deadlineNew) {
		this.deadlineNew = deadlineNew;
	}

	public Date getDeadlinePromote() {
		return deadlinePromote;
	}

	public void setDeadlinePromote(Date deadlinePromote) {
		this.deadlinePromote = deadlinePromote;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getBuyUrl() {
		return buyUrl;
	}

	public void setBuyUrl(String buyUrl) {
		this.buyUrl = buyUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(Long bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Boolean getIsStick() {
		return isStick;
	}

	public void setIsStick(Boolean isStick) {
		this.isStick = isStick;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Integer getSortNew() {
		return sortNew;
	}

	public void setSortNew(Integer sortNew) {
		this.sortNew = sortNew;
	}

	public Boolean getIsPromote() {
		return isPromote;
	}

	public void setIsPromote(Boolean isPromote) {
		this.isPromote = isPromote;
	}

	public Integer getSortPromote() {
		return sortPromote;
	}

	public void setSortPromote(Integer sortPromote) {
		this.sortPromote = sortPromote;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public Boolean getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(Boolean isOnSale) {
		this.isOnSale = isOnSale;
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

	public String getVn() {
		return vn;
	}

	public void setVn(String vn) {
		this.vn = vn;
	}

	@Override
	public String toString() {
		return "book [id=" + id + ", bookname=" + bookname + ", isbn=" + isbn + ", sn=" + sn + ", author=" + author
				+ ", publisher=" + publisher + ", lang=" + lang + ", revision=" + revision + ", type=" + type
				+ ", publishDate=" + publishDate + ", reader=" + reader + ", price=" + price + ", score=" + score
				+ ", buyUrl=" + buyUrl + ", imageUrl=" + imageUrl + ", pdfUrl=" + pdfUrl + ", clicks=" + clicks
				+ ", comments=" + comments + ", likes=" + likes + ", bookmarks=" + bookmarks + ", isStick=" + isStick
				+ ", sort=" + sort + ", deadlineStick=" + deadlineStick + ", isNew=" + isNew + ", sortNew=" + sortNew
				+ ", deadlineNew=" + deadlineNew + ", isPromote=" + isPromote + ", sortPromote=" + sortPromote
				+ ", deadlinePromote=" + deadlinePromote + ", sales=" + sales + ", isOnSale=" + isOnSale
				+ ", gmtCreate=" + gmtCreate + ", gmtUpdate=" + gmtUpdate + "]";
	}

}
