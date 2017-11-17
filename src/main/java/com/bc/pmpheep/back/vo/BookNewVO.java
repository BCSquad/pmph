package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述： 获取从商城接收的数据
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年11月15日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("BookNewVO")
public class BookNewVO implements Serializable {
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
	private Timestamp deadlineStick;
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
	private Timestamp deadlineNew;
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
	private Timestamp deadlinePromote;
	/**
	 * 销量
	 */
	private Long sales;
	/**
	 * 图书是否上架
	 */
	private Boolean isOnSale;
	/**
	 * 图书详情
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVn() {
		return vn;
	}

	public void setVn(String vn) {
		this.vn = vn;
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

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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

	public Timestamp getDeadlineStick() {
		return deadlineStick;
	}

	public void setDeadlineStick(Timestamp deadlineStick) {
		this.deadlineStick = deadlineStick;
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

	public Timestamp getDeadlineNew() {
		return deadlineNew;
	}

	public void setDeadlineNew(Timestamp deadlineNew) {
		this.deadlineNew = deadlineNew;
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

	public Timestamp getDeadlinePromote() {
		return deadlinePromote;
	}

	public void setDeadlinePromote(Timestamp deadlinePromote) {
		this.deadlinePromote = deadlinePromote;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

}
