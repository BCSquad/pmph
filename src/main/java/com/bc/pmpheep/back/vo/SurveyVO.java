package com.bc.pmpheep.back.vo;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 问卷实体类VO
 * 
 * @author tyc
 */
@Alias("SurveyVO")
public class SurveyVO implements java.io.Serializable {

    private static final long serialVersionUID = 1190785458692294163L;
    /**
     * 主键
     */
    private Long              id;
    /**
     * 问卷标题
     */
    private String            title;
    /**
     * 模版名称
     */
    private String            templateName;
    /**
     * 问卷副标题
     */
    private String            subhead;
    /**
     * 问卷简介
     */
    private String            intro;
    /**
     * 问卷模版类型
     */
    private Long              templateId;
    /**
     * 问卷调查类型
     */
    private Long              typeId;
    /**
     * 问卷创建人
     */
    private Long              userId;
    /**
     * 问卷开始时间(query)
     */
    private String            startTime;

    /**
     * 问卷结束时间(query)
     */
    private String            endTime;
    /**
     * 排序
     */
    private Integer           sort;
    /**
     * 是否被逻辑删除
     */
    private Boolean           isDeleted;
    /**
     * 问卷创建时间
     */
    private Timestamp         gmtCreate;
    /**
     * 问卷修改时间
     */
    private Timestamp         gmtUpdate;
    /**
     * 问卷发布时间
     */
    private Timestamp         gmtPublish;
    /**
     * 条件分页总条数分页查询
     */
    private Integer           count;
    /**
     * 页面查询条件（状态）
     */
    private Integer           status;
    /**
     * 问卷调查类型名称
     */
    private String            surveyName;
    /**
     * 用户名
     */
    private String            username;
    /**
     * 用户名
     */
    private String            realname;
    /**
     * 问卷开始时间
     */
    private Timestamp         beginDate;
    /**
     * 问卷结束时间
     */
    private Timestamp         endDate;

    private Long materialId;

    private Long preVersionMaterialId ;

    private String preVersionMaterialName ;

    private Integer preVersionMaterialRound ;
    //对绑定到书籍的调研表 是否必填（保存在中间表,此处只是接收）
    private Boolean requiredForWriter;
    //对绑定到教材的调研表 是否必填
    private Boolean  requiredForMaterial;
    //绑定到教材true 绑定到书籍false
    private Boolean allTextbookUsed;

    /**
     * 是否教材相关 仅做查询条件
     */
    private Boolean materialRelative ;

    /** default constructor */
    public SurveyVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
     * @return the beginDate
     */
    public Timestamp getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getPreVersionMaterialId() {
        return preVersionMaterialId;
    }

    public void setPreVersionMaterialId(Long preVersionMaterialId) {
        this.preVersionMaterialId = preVersionMaterialId;
    }

    public String getPreVersionMaterialName() {
        return preVersionMaterialName;
    }

    public void setPreVersionMaterialName(String preVersionMaterialName) {
        this.preVersionMaterialName = preVersionMaterialName;
    }

    public Integer getPreVersionMaterialRound() {
        return preVersionMaterialRound;
    }

    public void setPreVersionMaterialRound(Integer preVersionMaterialRound) {
        this.preVersionMaterialRound = preVersionMaterialRound;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Boolean getMaterialRelative() {
        return materialRelative;
    }

    public void setMaterialRelative(Boolean materialRelative) {
        this.materialRelative = materialRelative;
    }

    public Boolean getRequiredForWriter() {
        return requiredForWriter;
    }

    public void setRequiredForWriter(Boolean requiredForWriter) {
        this.requiredForWriter = requiredForWriter;
    }

    public Boolean getRequiredForMaterial() {
        return requiredForMaterial;
    }

    public void setRequiredForMaterial(Boolean requiredForMaterial) {
        this.requiredForMaterial = requiredForMaterial;
    }

    public Boolean getAllTextbookUsed() {
        return allTextbookUsed;
    }

    public void setAllTextbookUsed(Boolean allTextbookUsed) {
        this.allTextbookUsed = allTextbookUsed;
    }

    public Timestamp getGmtPublish() {
        return gmtPublish;
    }

    public void setGmtPublish(Timestamp gmtPublish) {
        this.gmtPublish = gmtPublish;
    }

    @Override
    public String toString() {
        return "SurveyVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", templateName='" + templateName + '\'' +
                ", subhead='" + subhead + '\'' +
                ", intro='" + intro + '\'' +
                ", templateId=" + templateId +
                ", typeId=" + typeId +
                ", userId=" + userId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", sort=" + sort +
                ", isDeleted=" + isDeleted +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
                ", gmtPublish=" + gmtPublish +
                ", count=" + count +
                ", status=" + status +
                ", surveyName='" + surveyName + '\'' +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", materialId=" + materialId +
                ", preVersionMaterialId=" + preVersionMaterialId +
                ", preVersionMaterialName='" + preVersionMaterialName + '\'' +
                ", preVersionMaterialRound=" + preVersionMaterialRound +
                ", requiredForWriter=" + requiredForWriter +
                ", requiredForMaterial=" + requiredForMaterial +
                ", allTextbookUsed=" + allTextbookUsed +
                ", materialRelative=" + materialRelative +
                '}';
    }
}