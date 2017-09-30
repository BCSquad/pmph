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
	//策划编辑id
	private Long planningEditor;
	//是否已选定第一主编
	private Boolean isChiefChosen;
	//是否已预选编委
	private Boolean isQualifierSelected;
	//主编是否选定编委
	private Boolean isListSelected;
	//策划编辑是否确定名单
	private Boolean isPlanningEditorConfirm;
	//项目编辑是否确定名单
	private Boolean isProjectEditorConfirm ;
	//是否锁定
	private Boolean isLocked;
	//是否已公布
	private Boolean isPublished;
	//公布时间
	private Timestamp gmtPublished;
	//公布后再次修改次数
	private Integer revisionTimes;
	//公布后再次公布次数
	private Integer republishTimes; 
	//ISBN号
	private String isbn;
	//图书序号
	private Integer sort;
	//创建人id
	private Long founderId;
	//创建时间
	private Timestamp gmtCreate;
	//修改时间
	private Timestamp gmtUpdate;


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

	/** minimal constructor 
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
	public Long getPlanningEditor() {
		return planningEditor;
	}
	public void setPlanningEditor(Long planningEditor) {
		this.planningEditor = planningEditor;
	}
	public Boolean getIsChiefChosen() {
		return isChiefChosen;
	}
	public void setIsChiefChosen(Boolean isChiefChosen) {
		this.isChiefChosen = isChiefChosen;
	}
	public Boolean getIsQualifierSelected() {
		return isQualifierSelected;
	}
	public void setIsQualifierSelected(Boolean isQualifierSelected) {
		this.isQualifierSelected = isQualifierSelected;
	}
	public Boolean getIsListSelected() {
		return isListSelected;
	}
	public void setIsListSelected(Boolean isListSelected) {
		this.isListSelected = isListSelected;
	}
	public Boolean getIsPlanningEditorConfirm() {
		return isPlanningEditorConfirm;
	}
	public void setIsPlanningEditorConfirm(Boolean isPlanningEditorConfirm) {
		this.isPlanningEditorConfirm = isPlanningEditorConfirm;
	}
	public Boolean getIsProjectEditorConfirm() {
		return isProjectEditorConfirm;
	}
	public void setIsProjectEditorConfirm(Boolean isProjectEditorConfirm) {
		this.isProjectEditorConfirm = isProjectEditorConfirm;
	}
	public Integer getRevisionTimes() {
		return revisionTimes;
	}
	public void setRevisionTimes(Integer revisionTimes) {
		this.revisionTimes = revisionTimes;
	}
	public Integer getRepublishTimes() {
		return republishTimes;
	}
	public void setRepublishTimes(Integer republishTimes) {
		this.republishTimes = republishTimes;
	}
	public Timestamp getGmtUpdate() {
		return gmtUpdate;
	}
	public void setGmtUpdate(Timestamp gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}
	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", textbookName:"
				+ textbookName + ", textbookRound:" + textbookRound
				+ ", planningEditor:" + planningEditor + ", isChiefChosen:"
				+ isChiefChosen + ", isQualifierSelected:"
				+ isQualifierSelected + ", isListSelected:" + isListSelected
				+ ", isPlanningEditorConfirm:" + isPlanningEditorConfirm
				+ ", isProjectEditorConfirm:" + isProjectEditorConfirm
				+ ", isLocked:" + isLocked + ", isPublished:" + isPublished
				+ ", gmtPublished:" + gmtPublished + ", revisionTimes:"
				+ revisionTimes + ", republishTimes:" + republishTimes
				+ ", isbn:" + isbn + ", sort:" + sort + ", founderId:"
				+ founderId + ", gmtCreate:" + gmtCreate + ", gmtUpdate:"
				+ gmtUpdate + "}";
	}

	
	
	
}