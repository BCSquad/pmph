package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("DecPositionDisplayVO")
public class DecPositionDisplayVO implements Serializable {
    // 主键
    private Long      id;
    // 申报表
    private Long      declarationId;
    // 书籍id
    private Long      textbookId;
    // 书籍名称
    private String    textbookName;
    // 申报职务
    private String   presetPosition;
    // 是否进入预选名单
    private Integer   isOnList;
    // 遴选职务
    private Integer   chosenPosition;
    // 排位
    private Integer   rank;
    // 教学大纲id
    private String    syllabusId;
    // 教学大纲名称
    private String    syllabusName;
    // 创建时间
    private Timestamp gmtCreate;
    // 修改时间
    private Timestamp gmtUpdate;
    // 显示申报职务
    private String showPosition;
    // 显示遴选职务
    private String showChosenPosition;

    public String getShowChosenPosition() {
		return showChosenPosition;
	}

	public void setShowChosenPosition(String showChosenPosition) {
		this.showChosenPosition = showChosenPosition;
	}

	public String getShowPosition() {
		return showPosition;
	}

	public void setShowPosition(String showPosition) {
		this.showPosition = showPosition;
	}

	public DecPositionDisplayVO() {
        super();
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

	public String getTextbookName() {
		return textbookName;
	}

	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}

	public String getPresetPosition() {
		return presetPosition;
	}

	public void setPresetPosition(String presetPosition) {
		this.presetPosition = presetPosition;
	}

	public Integer getIsOnList() {
		return isOnList;
	}

	public void setIsOnList(Integer isOnList) {
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
		return "DecPositionDisplayVO [id=" + id + ", declarationId="
				+ declarationId + ", textbookId=" + textbookId
				+ ", textbookName=" + textbookName + ", presetPosition="
				+ presetPosition + ", isOnList=" + isOnList
				+ ", chosenPosition=" + chosenPosition + ", rank=" + rank
				+ ", syllabusId=" + syllabusId + ", syllabusName="
				+ syllabusName + ", gmtCreate=" + gmtCreate + ", gmtUpdate="
				+ gmtUpdate + ", showPosition=" + showPosition
				+ ", showChosenPosition=" + showChosenPosition + "]";
	}
    
}
