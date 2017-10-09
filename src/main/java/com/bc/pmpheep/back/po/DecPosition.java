package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * DecPosition
 * 
 * @author mryang
 *
 */
@SuppressWarnings("serial")
@Alias("DecPosition")
public class DecPosition implements Serializable {
	//主键
	private Long  id;		
	//申报表
	private Long  declarationId;
	//书籍id	
	private Long  textbookId;
	//申报职务
	private Short presetPosition;	
	//是否进入预选名单
	private Boolean isOnList;		
	//遴选职务
	private Short chosenPosition;	
	//排位
	private Short rank;	
	//教学大纲id	
	private String syllabusId	;
	//教学大纲名称
	private String syllabusName;	
	//创建时间
	private Timestamp gmtCreate;		
	//修改时间	
	private Timestamp gmtUpdate;
	
	public DecPosition() {
		super();
	}
	public DecPosition(Long id) {
		super();
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDeclarationId() {
		return declarationId;
	}
	public void setDeclarationId(Long declarationId) {
		this.declarationId = declarationId;
	}
	public Long getTextbookId() {
		return textbookId;
	}
	public void setTextbookId(Long textbookId) {
		this.textbookId = textbookId;
	}
	public Short getPresetPosition() {
		return presetPosition;
	}
	public void setPresetPosition(Short presetPosition) {
		this.presetPosition = presetPosition;
	}
	public Boolean getIsOnList() {
		return isOnList;
	}
	public void setIsOnList(Boolean isOnList) {
		this.isOnList = isOnList;
	}
	public Short getChosenPosition() {
		return chosenPosition;
	}
	public void setChosenPosition(Short chosenPosition) {
		this.chosenPosition = chosenPosition;
	}
	public Short getRank() {
		return rank;
	}
	public void setRank(Short rank) {
		this.rank = rank;
	}
	public String getSyllabusId() {
		return syllabusId;
	}
	public void setSyllabusId(String syllabusId) {
		this.syllabusId = syllabusId;
	}
	public String getSyllabusName() {
		return syllabusName;
	}
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
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
	
	@Override
	public String toString() {
		return "{id:" + id + ", declarationId:" + declarationId
				+ ", textbookId:" + textbookId + ", presetPosition:"
				+ presetPosition + ", isOnList:" + isOnList
				+ ", chosenPosition:" + chosenPosition + ", rank:" + rank
				+ ", syllabusId:" + syllabusId + ", syllabusName:"
				+ syllabusName + ", gmtCreate:" + gmtCreate + ", gmtUpdate:"
				+ gmtUpdate + "}";
	}	
	
	
	

}
