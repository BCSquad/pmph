package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 */
@SuppressWarnings("serial")
@Alias("BookSyncConfirmVO")
public class BookSyncConfirmVO implements Serializable {
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

    private Long editorId;
    private Boolean confirmStatus;

    private Boolean isDeleted;
    private Long logId;
    private String synchronizationType;
    private Timestamp syncGmt;
    private Boolean increment;
    private Long confirmUser;

    public Long getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(Long confirmUser) {
        this.confirmUser = confirmUser;
    }


    public Boolean getIncrement() {
        return increment;
    }


    public void setIncrement(Boolean increment) {
        this.increment = increment;
    }

    public String getSynchronizationType() {
        return synchronizationType;
    }

    public void setSynchronizationType(String synchronizationType) {
        this.synchronizationType = synchronizationType;
    }

    public Timestamp getSyncGmt() {
        return syncGmt;
    }

    public void setSyncGmt(Timestamp syncGmt) {
        this.syncGmt = syncGmt;
    }

    public BookSyncConfirmVO() {
    }


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

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
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

    public Boolean getStick() {
        return isStick;
    }

    public void setStick(Boolean stick) {
        isStick = stick;
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

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
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

    public Boolean getPromote() {
        return isPromote;
    }

    public void setPromote(Boolean promote) {
        isPromote = promote;
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

    public Boolean getOnSale() {
        return isOnSale;
    }

    public void setOnSale(Boolean onSale) {
        isOnSale = onSale;
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

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public Boolean getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }
}
