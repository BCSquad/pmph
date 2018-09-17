package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 已公布作家申报职位表实体类
 * 
 * @author tyc 2018年1月15日 16:08
 */
@SuppressWarnings("serial")
@Alias("DecPositionPublished")
public class DecPositionPublished implements Serializable {
    // 主键
    private Long      id;
    // 公布人id
    private Long      publisherId;
    // 申报表
    private Long      declarationId;
    // 书籍id
    private Long      textbookId;
    // 申报职务
    private Integer   presetPosition;
    // 是否进入预选名单
    private Boolean   isOnList;
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
    // 申报人id
    private long      writerUserId;
    // 书籍名称
    private String    textbookName;

    public DecPositionPublished() {

    }

    public DecPositionPublished(Long declarationId) {
        this.declarationId = declarationId;
    }

    public DecPositionPublished(Long publisherId, Long declarationId, Long textbookId,
    Integer presetPosition, Boolean isOnList, Integer chosenPosition, Integer rank,
    String syllabusId, String syllabusName) {
        super();
        this.publisherId = publisherId;
        this.declarationId = declarationId;
        this.textbookId = textbookId;
        this.presetPosition = presetPosition;
        this.isOnList = isOnList;
        this.chosenPosition = chosenPosition;
        this.rank = rank;
        this.syllabusId = syllabusId;
        this.syllabusName = syllabusName;
    }

    public DecPositionPublished(Long publisherId, Long declarationId, Long textbookId,
    Integer presetPosition, Integer chosenPosition, Integer rank, String syllabusId,
    String syllabusName) {
        this.publisherId = publisherId;
        this.declarationId = declarationId;
        this.textbookId = textbookId;
        this.presetPosition = presetPosition;
        this.chosenPosition = chosenPosition;
        this.rank = rank;
        this.syllabusId = syllabusId;
        this.syllabusName = syllabusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
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

    public Boolean getIsOnList() {
        return isOnList;
    }

    public void setIsOnList(Boolean isOnList) {
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
        return "DecPositionPublished [id=" + id + ", publisherId=" + publisherId
               + ", declarationId=" + declarationId + ", textbookId=" + textbookId
               + ", presetPosition=" + presetPosition + ", isOnList=" + isOnList
               + ", chosenPosition=" + chosenPosition + ", rank=" + rank + ", syllabusId="
               + syllabusId + ", syllabusName=" + syllabusName + ", gmtCreate=" + gmtCreate
               + ", gmtUpdate=" + gmtUpdate + "]";
    }

    public long getWriterUserId() {
        return writerUserId;
    }

    public void setWriterUserId(long writerUserId) {
        this.writerUserId = writerUserId;
    }

    public String getTextbookName() {
        return textbookName;
    }

    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName;
    }
}
