package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;

/**
 * Textbook  教材书籍表     实体类 
 * @author mryang 
 *
 */
@SuppressWarnings("serial")
@Alias("Textbook")
public class Textbook implements java.io.Serializable {

	//主键
	private Long id;
	//教材id
	private Long materialId;
	//书籍名称
	private String textbookName;
	//书籍轮次
	private Integer textbookRound;
	//是否锁定
	private Boolean isLocked;
	//是否已公布
	private Boolean isPublished;
	//公布时间
	private Timestamp gmtPublished;
	//ISBN号
	private String isbn;
	//图书序号
	private Integer sort;
	//创建人id
	private Long founderId;
	//创建时间
	private Timestamp gmtCreate;

	// Constructors

	/** default constructor */
	public Textbook() {
	}
	/**
	 * 
	 */
	public Textbook(Long id){
		this.id=id;
	}
	/** minimal constructor */
	public Textbook(Long materialId, String textbookName,
			Integer textbookRound, Boolean isLocked, Boolean isPublished,
			Integer sort, Long founderId, Timestamp gmtCreate) {
		this.materialId = materialId;
		this.textbookName = textbookName;
		this.textbookRound = textbookRound;
		this.isLocked = isLocked;
		this.isPublished = isPublished;
		this.sort = sort;
		this.founderId = founderId;
		this.gmtCreate = gmtCreate;
	}

	/**
	 * 
	 * @param materialId
	 * @param textbookName
	 * @param textbookRound
	 * @param isLocked
	 * @param isPublished
	 * @param gmtPublished
	 * @param isbn
	 * @param sort
	 * @param founderId
	 * @param gmtCreate
	 */
	public Textbook(Long materialId, String textbookName,
			Integer textbookRound, Boolean isLocked, Boolean isPublished,
			Timestamp gmtPublished, String isbn, Integer sort, Long founderId,
			Timestamp gmtCreate) {
		this.materialId = materialId;
		this.textbookName = textbookName;
		this.textbookRound = textbookRound;
		this.isLocked = isLocked;
		this.isPublished = isPublished;
		this.gmtPublished = gmtPublished;
		this.isbn = isbn;
		this.sort = sort;
		this.founderId = founderId;
		this.gmtCreate = gmtCreate;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getTextbookName() {
		return this.textbookName;
	}

	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}

	public Integer getTextbookRound() {
		return this.textbookRound;
	}

	public void setTextbookRound(Integer textbookRound) {
		this.textbookRound = textbookRound;
	}

	public Boolean getIsLocked() {
		return this.isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Boolean getIsPublished() {
		return this.isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Timestamp getGmtPublished() {
		return this.gmtPublished;
	}

	public void setGmtPublished(Timestamp gmtPublished) {
		this.gmtPublished = gmtPublished;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getFounderId() {
		return this.founderId;
	}

	public void setFounderId(Long founderId) {
		this.founderId = founderId;
	}

	public Timestamp getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", textbookName:"
				+ textbookName + ", textbookRound:" + textbookRound
				+ ", isLocked:" + isLocked + ", isPublished:" + isPublished
				+ ", gmtPublished:" + gmtPublished + ", isbn:" + isbn
				+ ", sort:" + sort + ", founderId:" + founderId
				+ ", gmtCreate:" + gmtCreate + "}";
	}
	
	
}