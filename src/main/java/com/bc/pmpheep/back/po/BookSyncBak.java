package com.bc.pmpheep.back.po;;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * book_sync_bak
 * @author
 */
@Alias("BookSyncBak")
public class BookSyncBak implements Serializable {
	/**
	 * 主键
	 */
	private Long id;

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
	 * 类别（material_type）
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
	private BigDecimal price;

	/**
	 * 评分
	 */
	private BigDecimal score;

	/**
	 * 购买地址
	 */
	private String buyUrl;

	/**
	 * 图片地址
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
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtUpdate;

	/**
	 * 本版号
	 */
	private String vn;

	/**
	 * 所属教材id
	 */
	private Long materialId;

	/**
	 * 负责人id
	 */
	private Long editorId;

	/**
	 * 是否重点学科
	 */
	private Boolean isKey;

	/**
	 * 重点学科显示顺序
	 */
	private Integer sortKey;

	/**
	 * 是否热销榜
	 */
	private Boolean isSellWell;

	/**
	 * 热销榜排序
	 */
	private Integer sortSellWell;

	/**
	 * 操作类型
	 */
	private String synchronizationType;

	/**
	 * 确认时间
	 */
	private Date confirmGmt;

	/**
	 * 确认人
	 */
	private Long confirmUser;

	/**
	 * 来源图书id
	 */
	private Long bookSyncConfirmId;

	/**
	 * 修改的图书id
	 */
	private Long bookId;

	private static final long serialVersionUID = 1L;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
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

	public Date getDeadlineStick() {
		return deadlineStick;
	}

	public void setDeadlineStick(Date deadlineStick) {
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

	public Date getDeadlineNew() {
		return deadlineNew;
	}

	public void setDeadlineNew(Date deadlineNew) {
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

	public Date getDeadlinePromote() {
		return deadlinePromote;
	}

	public void setDeadlinePromote(Date deadlinePromote) {
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

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Date gmtUpdate) {
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

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
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

	public String getSynchronizationType() {
		return synchronizationType;
	}

	public void setSynchronizationType(String synchronizationType) {
		this.synchronizationType = synchronizationType;
	}

	public Date getConfirmGmt() {
		return confirmGmt;
	}

	public void setConfirmGmt(Date confirmGmt) {
		this.confirmGmt = confirmGmt;
	}

	public Long getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(Long confirmUser) {
		this.confirmUser = confirmUser;
	}

	public Long getBookSyncConfirmId() {
		return bookSyncConfirmId;
	}

	public void setBookSyncConfirmId(Long bookSyncConfirmId) {
		this.bookSyncConfirmId = bookSyncConfirmId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		BookSyncBak other = (BookSyncBak) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getBookname() == null ? other.getBookname() == null : this.getBookname().equals(other.getBookname()))
				&& (this.getIsbn() == null ? other.getIsbn() == null : this.getIsbn().equals(other.getIsbn()))
				&& (this.getSn() == null ? other.getSn() == null : this.getSn().equals(other.getSn()))
				&& (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
				&& (this.getPublisher() == null ? other.getPublisher() == null : this.getPublisher().equals(other.getPublisher()))
				&& (this.getLang() == null ? other.getLang() == null : this.getLang().equals(other.getLang()))
				&& (this.getRevision() == null ? other.getRevision() == null : this.getRevision().equals(other.getRevision()))
				&& (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
				&& (this.getPublishDate() == null ? other.getPublishDate() == null : this.getPublishDate().equals(other.getPublishDate()))
				&& (this.getReader() == null ? other.getReader() == null : this.getReader().equals(other.getReader()))
				&& (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
				&& (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
				&& (this.getBuyUrl() == null ? other.getBuyUrl() == null : this.getBuyUrl().equals(other.getBuyUrl()))
				&& (this.getImageUrl() == null ? other.getImageUrl() == null : this.getImageUrl().equals(other.getImageUrl()))
				&& (this.getPdfUrl() == null ? other.getPdfUrl() == null : this.getPdfUrl().equals(other.getPdfUrl()))
				&& (this.getClicks() == null ? other.getClicks() == null : this.getClicks().equals(other.getClicks()))
				&& (this.getComments() == null ? other.getComments() == null : this.getComments().equals(other.getComments()))
				&& (this.getLikes() == null ? other.getLikes() == null : this.getLikes().equals(other.getLikes()))
				&& (this.getBookmarks() == null ? other.getBookmarks() == null : this.getBookmarks().equals(other.getBookmarks()))
				&& (this.getIsStick() == null ? other.getIsStick() == null : this.getIsStick().equals(other.getIsStick()))
				&& (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
				&& (this.getDeadlineStick() == null ? other.getDeadlineStick() == null : this.getDeadlineStick().equals(other.getDeadlineStick()))
				&& (this.getIsNew() == null ? other.getIsNew() == null : this.getIsNew().equals(other.getIsNew()))
				&& (this.getSortNew() == null ? other.getSortNew() == null : this.getSortNew().equals(other.getSortNew()))
				&& (this.getDeadlineNew() == null ? other.getDeadlineNew() == null : this.getDeadlineNew().equals(other.getDeadlineNew()))
				&& (this.getIsPromote() == null ? other.getIsPromote() == null : this.getIsPromote().equals(other.getIsPromote()))
				&& (this.getSortPromote() == null ? other.getSortPromote() == null : this.getSortPromote().equals(other.getSortPromote()))
				&& (this.getDeadlinePromote() == null ? other.getDeadlinePromote() == null : this.getDeadlinePromote().equals(other.getDeadlinePromote()))
				&& (this.getSales() == null ? other.getSales() == null : this.getSales().equals(other.getSales()))
				&& (this.getIsOnSale() == null ? other.getIsOnSale() == null : this.getIsOnSale().equals(other.getIsOnSale()))
				&& (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
				&& (this.getGmtUpdate() == null ? other.getGmtUpdate() == null : this.getGmtUpdate().equals(other.getGmtUpdate()))
				&& (this.getVn() == null ? other.getVn() == null : this.getVn().equals(other.getVn()))
				&& (this.getMaterialId() == null ? other.getMaterialId() == null : this.getMaterialId().equals(other.getMaterialId()))
				&& (this.getEditorId() == null ? other.getEditorId() == null : this.getEditorId().equals(other.getEditorId()))
				&& (this.getIsKey() == null ? other.getIsKey() == null : this.getIsKey().equals(other.getIsKey()))
				&& (this.getSortKey() == null ? other.getSortKey() == null : this.getSortKey().equals(other.getSortKey()))
				&& (this.getIsSellWell() == null ? other.getIsSellWell() == null : this.getIsSellWell().equals(other.getIsSellWell()))
				&& (this.getSortSellWell() == null ? other.getSortSellWell() == null : this.getSortSellWell().equals(other.getSortSellWell()))
				&& (this.getSynchronizationType() == null ? other.getSynchronizationType() == null : this.getSynchronizationType().equals(other.getSynchronizationType()))
				&& (this.getConfirmGmt() == null ? other.getConfirmGmt() == null : this.getConfirmGmt().equals(other.getConfirmGmt()))
				&& (this.getConfirmUser() == null ? other.getConfirmUser() == null : this.getConfirmUser().equals(other.getConfirmUser()))
				&& (this.getBookSyncConfirmId() == null ? other.getBookSyncConfirmId() == null : this.getBookSyncConfirmId().equals(other.getBookSyncConfirmId()))
				&& (this.getBookId() == null ? other.getBookId() == null : this.getBookId().equals(other.getBookId()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getBookname() == null) ? 0 : getBookname().hashCode());
		result = prime * result + ((getIsbn() == null) ? 0 : getIsbn().hashCode());
		result = prime * result + ((getSn() == null) ? 0 : getSn().hashCode());
		result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
		result = prime * result + ((getPublisher() == null) ? 0 : getPublisher().hashCode());
		result = prime * result + ((getLang() == null) ? 0 : getLang().hashCode());
		result = prime * result + ((getRevision() == null) ? 0 : getRevision().hashCode());
		result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
		result = prime * result + ((getPublishDate() == null) ? 0 : getPublishDate().hashCode());
		result = prime * result + ((getReader() == null) ? 0 : getReader().hashCode());
		result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
		result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
		result = prime * result + ((getBuyUrl() == null) ? 0 : getBuyUrl().hashCode());
		result = prime * result + ((getImageUrl() == null) ? 0 : getImageUrl().hashCode());
		result = prime * result + ((getPdfUrl() == null) ? 0 : getPdfUrl().hashCode());
		result = prime * result + ((getClicks() == null) ? 0 : getClicks().hashCode());
		result = prime * result + ((getComments() == null) ? 0 : getComments().hashCode());
		result = prime * result + ((getLikes() == null) ? 0 : getLikes().hashCode());
		result = prime * result + ((getBookmarks() == null) ? 0 : getBookmarks().hashCode());
		result = prime * result + ((getIsStick() == null) ? 0 : getIsStick().hashCode());
		result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
		result = prime * result + ((getDeadlineStick() == null) ? 0 : getDeadlineStick().hashCode());
		result = prime * result + ((getIsNew() == null) ? 0 : getIsNew().hashCode());
		result = prime * result + ((getSortNew() == null) ? 0 : getSortNew().hashCode());
		result = prime * result + ((getDeadlineNew() == null) ? 0 : getDeadlineNew().hashCode());
		result = prime * result + ((getIsPromote() == null) ? 0 : getIsPromote().hashCode());
		result = prime * result + ((getSortPromote() == null) ? 0 : getSortPromote().hashCode());
		result = prime * result + ((getDeadlinePromote() == null) ? 0 : getDeadlinePromote().hashCode());
		result = prime * result + ((getSales() == null) ? 0 : getSales().hashCode());
		result = prime * result + ((getIsOnSale() == null) ? 0 : getIsOnSale().hashCode());
		result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
		result = prime * result + ((getGmtUpdate() == null) ? 0 : getGmtUpdate().hashCode());
		result = prime * result + ((getVn() == null) ? 0 : getVn().hashCode());
		result = prime * result + ((getMaterialId() == null) ? 0 : getMaterialId().hashCode());
		result = prime * result + ((getEditorId() == null) ? 0 : getEditorId().hashCode());
		result = prime * result + ((getIsKey() == null) ? 0 : getIsKey().hashCode());
		result = prime * result + ((getSortKey() == null) ? 0 : getSortKey().hashCode());
		result = prime * result + ((getIsSellWell() == null) ? 0 : getIsSellWell().hashCode());
		result = prime * result + ((getSortSellWell() == null) ? 0 : getSortSellWell().hashCode());
		result = prime * result + ((getSynchronizationType() == null) ? 0 : getSynchronizationType().hashCode());
		result = prime * result + ((getConfirmGmt() == null) ? 0 : getConfirmGmt().hashCode());
		result = prime * result + ((getConfirmUser() == null) ? 0 : getConfirmUser().hashCode());
		result = prime * result + ((getBookSyncConfirmId() == null) ? 0 : getBookSyncConfirmId().hashCode());
		result = prime * result + ((getBookId() == null) ? 0 : getBookId().hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", bookname=").append(bookname);
		sb.append(", isbn=").append(isbn);
		sb.append(", sn=").append(sn);
		sb.append(", author=").append(author);
		sb.append(", publisher=").append(publisher);
		sb.append(", lang=").append(lang);
		sb.append(", revision=").append(revision);
		sb.append(", type=").append(type);
		sb.append(", publishDate=").append(publishDate);
		sb.append(", reader=").append(reader);
		sb.append(", price=").append(price);
		sb.append(", score=").append(score);
		sb.append(", buyUrl=").append(buyUrl);
		sb.append(", imageUrl=").append(imageUrl);
		sb.append(", pdfUrl=").append(pdfUrl);
		sb.append(", clicks=").append(clicks);
		sb.append(", comments=").append(comments);
		sb.append(", likes=").append(likes);
		sb.append(", bookmarks=").append(bookmarks);
		sb.append(", isStick=").append(isStick);
		sb.append(", sort=").append(sort);
		sb.append(", deadlineStick=").append(deadlineStick);
		sb.append(", isNew=").append(isNew);
		sb.append(", sortNew=").append(sortNew);
		sb.append(", deadlineNew=").append(deadlineNew);
		sb.append(", isPromote=").append(isPromote);
		sb.append(", sortPromote=").append(sortPromote);
		sb.append(", deadlinePromote=").append(deadlinePromote);
		sb.append(", sales=").append(sales);
		sb.append(", isOnSale=").append(isOnSale);
		sb.append(", gmtCreate=").append(gmtCreate);
		sb.append(", gmtUpdate=").append(gmtUpdate);
		sb.append(", vn=").append(vn);
		sb.append(", materialId=").append(materialId);
		sb.append(", editorId=").append(editorId);
		sb.append(", isKey=").append(isKey);
		sb.append(", sortKey=").append(sortKey);
		sb.append(", isSellWell=").append(isSellWell);
		sb.append(", sortSellWell=").append(sortSellWell);
		sb.append(", synchronizationType=").append(synchronizationType);
		sb.append(", confirmGmt=").append(confirmGmt);
		sb.append(", confirmUser=").append(confirmUser);
		sb.append(", bookSyncConfirmId=").append(bookSyncConfirmId);
		sb.append(", bookId=").append(bookId);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}