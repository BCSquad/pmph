package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：作家职位申报表暂存表
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年11月23日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@SuppressWarnings("serial")
@Alias("DecPositionTemp")
public class DecPositionTemp implements Serializable {
	// 主键
	private Long id;
	// 暂存人id
	private Long authorId;
	// 暂存人是否社内用户
	private Boolean isBackground;
	// 申报表
	private Long declarationId;
	// 书籍id
	private Long textbookId;
	// 申报职务
	private Integer presetPosition;
	// 是否进入预选名单
	private boolean isOnList;
	// 遴选职务
	private Integer chosenPosition;
	// 排位
	private Integer rank;
	// 教学大纲id
	private String syllabusId;
	// 教学大纲名称
	private String syllabusName;
	// 创建时间
	private Timestamp gmtCreate;
	// 修改时间
	private Timestamp gmtUpdate;

	public DecPositionTemp(Long authorId, Boolean isBackground, Long declarationId, Long textbookId,
			Integer presetPosition,  boolean isOnList, Integer chosenPosition, Integer rank,
			String syllabusId, String syllabusName, Timestamp gmtCreate, Timestamp gmtUpdate) {
		super();
		this.authorId = authorId;
		this.isBackground = isBackground;
		this.declarationId = declarationId;
		this.textbookId = textbookId;
		this.presetPosition = presetPosition;
		this.isOnList = isOnList;
		this.chosenPosition = chosenPosition;
		this.rank = rank;
		this.syllabusId = syllabusId;
		this.syllabusName = syllabusName;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Boolean getIsBackground() {
		return isBackground;
	}

	public void setIsBackground(Boolean isBackground) {
		this.isBackground = isBackground;
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

	public Integer getPresetPosition() {
		return presetPosition;
	}

	public void setPresetPosition(Integer presetPosition) {
		this.presetPosition = presetPosition;
	}

	
	public boolean getIsOnList() {
		return isOnList;
	}

	public void setIsOnList(boolean isOnList) {
		this.isOnList = isOnList;
	}

	public Integer getChosenPosition() {
		return chosenPosition;
	}

	public void setChosenPosition(Integer chosenPosition) {
		this.chosenPosition = chosenPosition;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
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
		return "{id:" + id + ", authorId:" + authorId + ", isBackground:"
				+ isBackground + ", declarationId:" + declarationId
				+ ", textbookId:" + textbookId + ", presetPosition:"
				+ presetPosition + ", isOnList:" + isOnList
				+ ", chosenPosition:" + chosenPosition + ", rank:" + rank
				+ ", syllabusId:" + syllabusId + ", syllabusName:"
				+ syllabusName + ", gmtCreate:" + gmtCreate + ", gmtUpdate:"
				+ gmtUpdate + "}";
	}

}
