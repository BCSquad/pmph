package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

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
@SuppressWarnings("serial")
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
	 * 教材id
	 */
	private Long materialId;
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
	/**
	 * 是否重点学科
	 */
	private Boolean isKey;
	/**
	 * 重点学科显示顺序
	 */
	private Integer sortKey;
	private Boolean  isSellWell;
	private Integer sortSellWell;
	private Boolean isDelected;
	private Boolean isNewBook;
	private Integer sortNewBook;
	private Boolean isHighly;
	private Integer sortHighly;
	private Integer flag;


	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Boolean getIsNewBook() {
		return isNewBook;
	}

	public void setIsNewBook(Boolean newBook) {
		isNewBook = newBook;
	}

	public Integer getSortNewBook() {
		return sortNewBook;
	}

	public void setSortNewBook(Integer sortNewBook) {
		this.sortNewBook = sortNewBook;
	}

	public Boolean getIsHighly() {
		return isHighly;
	}

	public void setIsHighly(Boolean IsHighly) {
		isHighly = IsHighly;
	}

	public Integer getSortHighly() {
		return sortHighly;
	}

	public void setSortHighly(Integer sortHighly) {
		this.sortHighly = sortHighly;
	}

	public Boolean getIsDelected() {
		return isDelected;
	}

	public void setIsDelected(Boolean delected) {
		isDelected = delected;
	}

	public Boolean getIsSellWell() {
		return isSellWell;
	}

	public void setIsSellWell(Boolean isSellWell) {
		this.isSellWell = isSellWell;
	}

	public Integer getSortSellWell() {
		return sortSellWell;
	}

	public void setSortSellWell(Integer sortSellWell) {
		this.sortSellWell = sortSellWell;
	}

	public Book() {
		super();
	}

	public Book(Long id, String bookname, String isbn) {
		this.id = id;
		this.bookname = bookname;
		this.isbn = isbn;
	}

	public Book(String bookname, String isbn, String sn, String author, String publisher, String lang, Integer revision,
				Long type, Date publishDate, String reader, Double price, Double score, String buyUrl, String imageUrl,
				String pdfUrl, Long clicks, Long comments, Long likes, Long bookmarks, Boolean isStick, Integer sort,
				Timestamp deadlineStick, Boolean isNew, Integer sortNew, Timestamp deadlineNew, Boolean isPromote,
				Integer sortPromote, Timestamp deadlinePromote, Boolean isKey, Integer sortKey, Long sales, Boolean isOnSale,
				Timestamp gmtCreate, Timestamp gmtUpdate) {
		super();
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
		this.isKey = isKey;
		this.sortKey = sortKey;
		this.sales = sales;
		this.isOnSale = isOnSale;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
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

	public String getVn() {
		return vn;
	}

	public void setVn(String vn) {
		this.vn = vn;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Boolean getIsKey() {
		return isKey;
	}

	public void setIsKey(Boolean isKey) {
		this.isKey = isKey;
	}

	public Integer getSortKey() {
		return sortKey;
	}

	public void setSortKey(Integer sortKey) {
		this.sortKey = sortKey;
	}


	public Book(Long id, String vn, String bookname, String isbn, String sn, String author, String publisher,
				String lang, Integer revision, Long type, Date publishDate, String reader, Double price, Double score,
				String buyUrl, Long materialId, String imageUrl, String pdfUrl, Long clicks, Long comments, Long likes,
				Long bookmarks, Boolean isStick, Integer sort, Timestamp deadlineStick, Boolean isNew, Integer sortNew,
				Timestamp deadlineNew, Boolean isPromote, Integer sortPromote, Timestamp deadlinePromote, Long sales,
				Boolean isOnSale, String content, Timestamp gmtCreate, Timestamp gmtUpdate, Boolean isKey,
				Integer sortKey,Boolean isSellWell,Integer sortSellWell) {
		this.id = id;
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
		this.materialId = materialId;
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
		this.content = content;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
		this.isKey = isKey;
		this.sortKey = sortKey;
		this.isSellWell=isSellWell;
		this.sortSellWell=sortSellWell;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", vn=" + vn + ", bookname=" + bookname + ", isbn=" + isbn + ", sn=" + sn
				+ ", author=" + author + ", publisher=" + publisher + ", lang=" + lang + ", revision=" + revision
				+ ", type=" + type + ", publishDate=" + publishDate + ", reader=" + reader + ", price=" + price
				+ ", score=" + score + ", buyUrl=" + buyUrl + ", materialId=" + materialId + ", imageUrl=" + imageUrl
				+ ", pdfUrl=" + pdfUrl + ", clicks=" + clicks + ", comments=" + comments + ", likes=" + likes
				+ ", bookmarks=" + bookmarks + ", isStick=" + isStick + ", sort=" + sort + ", deadlineStick="
				+ deadlineStick + ", isNew=" + isNew + ", sortNew=" + sortNew + ", deadlineNew=" + deadlineNew
				+ ", isPromote=" + isPromote + ", sortPromote=" + sortPromote + ", deadlinePromote=" + deadlinePromote
				+ ", sales=" + sales + ", isOnSale=" + isOnSale + ", content=" + content + ", gmtCreate=" + gmtCreate
				+ ", gmtUpdate=" + gmtUpdate + ", isKey=" + isKey + ", sortKey=" + sortKey + "]";
	}


}
