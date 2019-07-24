package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：教材申报-遴选主编/遴选编委VO 
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-27
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("serial")
@Alias("DecPositionEditorSelectionVO")
public class DecPositionEditorSelectionVO implements Serializable {
    // 主键
    private Long      id;
    // 申报表
    private Long      declarationId;
    // 书籍id
    private Long      textbookId;
    // 申报职务
    private String   presetPosition;
    // 申报职务
    private String    strPresetPosition;
    // 遴选职务
    private Integer   chosenPosition;
    // 排位
    private Integer   rank;
    // 是否为数字编辑
    private Boolean   isDigitalEditor;
    // 是否可选数字编委
    private Boolean   isDigitalEditorOptional;
    // 教学大纲id
    private String    syllabusId;
    // 教学大纲名称
    private String    syllabusName;

    // 作家id
    private Long      userId;
    // 作家真实姓名
    private String    realname;
    // 作家性别
    private Integer   sex;
    // 作家工作单位
    private String    orgName;
    // 作家申报单位id
    private Long      orgId;
    // 作家申报单位
    private String    reportName;
    // 审核进度
    private Integer   onlineProgress;
    // 纸质表进度
    private Integer   offlineProgress;
    // 创建时间
    private Timestamp gmtCreate;
    private Integer did;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the declarationId
     */
    public Long getDeclarationId() {
        return declarationId;
    }

    /**
     * @param declarationId the declarationId to set
     */
    public void setDeclarationId(Long declarationId) {
        this.declarationId = declarationId;
    }

    /**
     * @return the textbookId
     */
    public Long getTextbookId() {
        return textbookId;
    }

    /**
     * @param textbookId the textbookId to set
     */
    public void setTextbookId(Long textbookId) {
        this.textbookId = textbookId;
    }

    /**
     * @return the presetPosition
     */
    public String getPresetPosition() {
        return presetPosition;
    }

    /**
     * @param presetPosition the presetPosition to set
     */
    public void setPresetPosition(String presetPosition) {
        this.presetPosition = presetPosition;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return the sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the gmtCreate
     */
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate the gmtCreate to set
     */
    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return the isDigitalEditor
     */
    public Boolean getIsDigitalEditor() {
        return isDigitalEditor;
    }

    /**
     * @param isDigitalEditor the isDigitalEditor to set
     */
    public void setIsDigitalEditor(Boolean isDigitalEditor) {
        this.isDigitalEditor = isDigitalEditor;
    }

    /**
     * @return the onlineProgress
     */
    public Integer getOnlineProgress() {
        return onlineProgress;
    }

    /**
     * @param onlineProgress the onlineProgress to set
     */
    public void setOnlineProgress(Integer onlineProgress) {
        this.onlineProgress = onlineProgress;
    }

    /**
     * @return the offlineProgress
     */
    public Integer getOfflineProgress() {
        return offlineProgress;
    }

    /**
     * @param offlineProgress the offlineProgress to set
     */
    public void setOfflineProgress(Integer offlineProgress) {
        this.offlineProgress = offlineProgress;
    }

    /**
     * @return the chosenPosition
     */
    public Integer getChosenPosition() {
        return chosenPosition;
    }

    /**
     * @param chosenPosition the chosenPosition to set
     */
    public void setChosenPosition(Integer chosenPosition) {
        this.chosenPosition = chosenPosition;
    }

    /**
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * @return the isDigitalEditorOptional
     */
    public Boolean getIsDigitalEditorOptional() {
        return isDigitalEditorOptional;
    }

    /**
     * @param isDigitalEditorOptional the isDigitalEditorOptional to set
     */
    public void setIsDigitalEditorOptional(Boolean isDigitalEditorOptional) {
        this.isDigitalEditorOptional = isDigitalEditorOptional;
    }

    /**
     * @return the strPresetPosition
     */
    public String getStrPresetPosition() {
        return strPresetPosition;
    }

    /**
     * @param strPresetPosition the strPresetPosition to set
     */
    public void setStrPresetPosition(String strPresetPosition) {
        this.strPresetPosition = strPresetPosition;
    }

    /**
     * @return the syllabusId
     */
    public String getSyllabusId() {
        return syllabusId;
    }

    /**
     * @param syllabusId the syllabusId to set
     */
    public void setSyllabusId(String syllabusId) {
        this.syllabusId = syllabusId;
    }

    /**
     * @return the syllabusName
     */
    public String getSyllabusName() {
        return syllabusName;
    }

    /**
     * @param syllabusName the syllabusName to set
     */
    public void setSyllabusName(String syllabusName) {
        this.syllabusName = syllabusName;
    }

    /**
     * <pre>
     * 功能描述：
     * 使用示范：
     *
     * @return
     * </pre>
     */
    @Override
    public String toString() {
        return "DecPositionEditorSelectionVO [id=" + id + ", declarationId=" + declarationId
               + ", textbookId=" + textbookId + ", presetPosition=" + presetPosition
               + ", strPresetPosition=" + strPresetPosition + ", chosenPosition=" + chosenPosition
               + ", rank=" + rank + ", isDigitalEditor=" + isDigitalEditor
               + ", isDigitalEditorOptional=" + isDigitalEditorOptional + ", syllabusId="
               + syllabusId + ", syllabusName=" + syllabusName + ", userId=" + userId
               + ", realname=" + realname + ", sex=" + sex + ", orgName=" + orgName + ", orgId="
               + orgId + ", reportName=" + reportName + ", onlineProgress=" + onlineProgress
               + ", offlineProgress=" + offlineProgress + ", gmtCreate=" + gmtCreate + "]";
    }

}
