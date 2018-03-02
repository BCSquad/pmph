package com.bc.pmpheep.back.po;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * Material 教材信息表 实体类
 * 
 * @author mryang
 * 修改：tyc
 * 
 */
@SuppressWarnings("serial")
@Alias("Material")
public class Material implements java.io.Serializable {
    // 主键
    private Long      id;
    // 教材名称
    private String    materialName;
    // 教材轮次
    private Integer   materialRound;
    // 教材类型
    private Long      materialType;
    // 显示报名截止日期
    private Date      deadline;
    // 实际报名截止日期
    private Date      actualDeadline;
    // 年龄计算截止日期
    private Date      ageDeadline;
    // 邮寄地址
    private String    mailAddress;
    // 教材所属部门
    private Long      departmentId;
    // 主任id
    private Long      director;
    // 是否书籍多选
    private Boolean   isMultiBooks;
    // 是否职位多选
    private Boolean   isMultiPosition;
    // 学习经历
    private Boolean   isEduExpUsed;
    // 学习经历必填
    private Boolean   isEduExpRequired;
    // 工作经历
    private Boolean   isWorkExpUsed;
    // 工作经历必填
    private Boolean   isWorkExpRequired;
    // 教学经历
    private Boolean   isTeachExpUsed;
    // 教学经历必填
    private Boolean   isTeachExpRequired;
    // 主要学术兼职
    private Boolean   isAcadeUsed;
    // 主要学术兼职必填
    private Boolean   isAcadeRequired;
    // 上版教材参编情况
    private Boolean   isLastPositionUsed;
    // 上版教材参编情况必填
    private Boolean   isLastPositionRequired;
    // 课程建设情况
    private Boolean   isCourseUsed;
    // 课程建设情况必填
    private Boolean   isCourseRequired;
    // 主编国家规划教材情况
    private Boolean   isNationalPlanUsed;
    // 主编国家规划教材情况必填
    private Boolean   isNationalPlanRequired;
    // 其他社教材编写情况
    private Boolean   isTextbookUsed;
    // 其他社教材编写情况必填
    private Boolean   isTextbookRequired;
    // 人卫社教材编写情况
    private Boolean   isPmphTextbookUsed;
    // 人卫社教材编写情况必填
    private Boolean   isPmphTextbookRequired;
    // 科研情况
    private Boolean   isResearchUsed;
    // 科研情况必填
    private Boolean   isResearchRequired;
    // 是否已发布到前台
    private Boolean   isPublished;
    // 可见性区别
    private Boolean   isPublic;
    // 是否所有书籍已公布
    private Boolean   isAllTextbookPublished;
    // 是否被强制结束
    private Boolean   isForceEnd;
    // 是否被逻辑删除
    private Boolean   isDeleted;
    // 创建时间
    private Timestamp gmtCreate;
    // 创建人id
    private Long      founderId;
    // 修改时间
    private Timestamp gmtUpdate;
    // 修改人id
    private Long      menderId;
    //项目编辑权限
    private Integer projectPermission ;
    //策划编辑权限
    private Integer planPermission ;
    //个人成就
    private Boolean isAchievementUsed    ;
    //个人成就可用
    private Boolean isAchievementRequired;
    //是否可选数字编委
    private Boolean isDigitalEditorOptional ;
    //主编学术专著情况 
    private Boolean isMonographUsed;
    //主编学术专著情况必填
    private Boolean isMonographRequired;
    //出版行业获奖情况
    private Boolean isPublishRewardUsed;
    //出版行业获奖情况必填
    private Boolean isPublishRewardRequired;
    //SCI论文投稿及影响因子
    private Boolean isSciUsed;
    //SCI论文投稿及影响因子必填
    private Boolean isSciRequired;
    //临床医学获奖情况
    private Boolean isClinicalRewardUsed;
    //临床医学获奖情况必填
    private Boolean isClinicalRewardRequired;
    //学术荣誉授予情况
    private Boolean isAcadeRewardUsed;
    //学术荣誉授予情况必填
    private Boolean isAcadeRewardRequired;
    // 人卫慕课、数字教材编写情况
    private Boolean isMoocDigitalUsed;
    // 人卫慕课、数字教材编写情况必填
    private Boolean isMoocDigitalRequired;
    // 编写内容意向
    private Boolean isIntentionUsed;
    // 编写内容意向必填
    private Boolean isIntentionRequired;

    // Constructors
	/** default constructor */
    public Material() {
    }

    public Material(Long id) {
        super();
        this.id = id;
    }

    public Material(Long id,Boolean isPublished) {
        super();
        this.id = id;
        this.isPublished = isPublished;
    }
    
    
    /**
     * @param materialName
     * @param materialRound
     * @param materialType
     * @param deadline
     * @param actualDeadline
     * @param ageDeadline
     * @param mailAddress
     * @param departmentId
     * @param director
     * @param founderId
     * @param menderId
     * @param projectPermission
     * @param planPermission
     */
    public Material(String materialName, Integer materialRound,
			Long materialType, Date deadline, Date actualDeadline,
			Date ageDeadline, String mailAddress, Long departmentId,
			Long director, Long founderId, Long menderId,
			Integer projectPermission, Integer planPermission) {
		super();
		this.materialName = materialName;
		this.materialRound = materialRound;
		this.materialType = materialType;
		this.deadline = deadline;
		this.actualDeadline = actualDeadline;
		this.ageDeadline = ageDeadline;
		this.mailAddress = mailAddress;
		this.departmentId = departmentId;
		this.director = director;
		this.founderId = founderId;
		this.menderId = menderId;
		this.projectPermission = projectPermission;
		this.planPermission = planPermission;
	}
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return this.materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getMaterialRound() {
        return this.materialRound;
    }

    public void setMaterialRound(Integer materialRound) {
        this.materialRound = materialRound;
    }

    public Long getMaterialType() {
        return this.materialType;
    }

    public void setMaterialType(Long materialType) {
        this.materialType = materialType;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getActualDeadline() {
        return this.actualDeadline;
    }

    public void setActualDeadline(Date actualDeadline) {
        this.actualDeadline = actualDeadline;
    }

    public Date getAgeDeadline() {
        return this.ageDeadline;
    }

    public void setAgeDeadline(Date ageDeadline) {
        this.ageDeadline = ageDeadline;
    }

    public String getMailAddress() {
        return this.mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDirector() {
        return this.director;
    }

    public void setDirector(Long director) {
        this.director = director;
    }

    public Boolean getIsMultiBooks() {
        return this.isMultiBooks;
    }

    public void setIsMultiBooks(Boolean isMultiBooks) {
        this.isMultiBooks = isMultiBooks;
    }

    public Boolean getIsMultiPosition() {
        return this.isMultiPosition;
    }

    public void setIsMultiPosition(Boolean isMultiPosition) {
        this.isMultiPosition = isMultiPosition;
    }

    public Boolean getIsEduExpUsed() {
        return this.isEduExpUsed;
    }

    public void setIsEduExpUsed(Boolean isEduExpUsed) {
        this.isEduExpUsed = isEduExpUsed;
    }

    public Boolean getIsEduExpRequired() {
        return this.isEduExpRequired;
    }

    public void setIsEduExpRequired(Boolean isEduExpRequired) {
        this.isEduExpRequired = isEduExpRequired;
    }

    public Boolean getIsWorkExpUsed() {
        return this.isWorkExpUsed;
    }

    public void setIsWorkExpUsed(Boolean isWorkExpUsed) {
        this.isWorkExpUsed = isWorkExpUsed;
    }

    public Boolean getIsWorkExpRequired() {
        return this.isWorkExpRequired;
    }

    public void setIsWorkExpRequired(Boolean isWorkExpRequired) {
        this.isWorkExpRequired = isWorkExpRequired;
    }

    public Boolean getIsTeachExpUsed() {
        return this.isTeachExpUsed;
    }

    public void setIsTeachExpUsed(Boolean isTeachExpUsed) {
        this.isTeachExpUsed = isTeachExpUsed;
    }

    public Boolean getIsTeachExpRequired() {
        return this.isTeachExpRequired;
    }

    public void setIsTeachExpRequired(Boolean isTeachExpRequired) {
        this.isTeachExpRequired = isTeachExpRequired;
    }

    public Boolean getIsAcadeUsed() {
        return this.isAcadeUsed;
    }

    public void setIsAcadeUsed(Boolean isAcadeUsed) {
        this.isAcadeUsed = isAcadeUsed;
    }

    public Boolean getIsAcadeRequired() {
        return this.isAcadeRequired;
    }

    public void setIsAcadeRequired(Boolean isAcadeRequired) {
        this.isAcadeRequired = isAcadeRequired;
    }

    public Boolean getIsLastPositionUsed() {
        return this.isLastPositionUsed;
    }

    public void setIsLastPositionUsed(Boolean isLastPositionUsed) {
        this.isLastPositionUsed = isLastPositionUsed;
    }

    public Boolean getIsLastPositionRequired() {
        return this.isLastPositionRequired;
    }

    public void setIsLastPositionRequired(Boolean isLastPositionRequired) {
        this.isLastPositionRequired = isLastPositionRequired;
    }

    public Boolean getIsCourseUsed() {
		return isCourseUsed;
	}

	public void setIsCourseUsed(Boolean isCourseUsed) {
		this.isCourseUsed = isCourseUsed;
	}

	public Boolean getIsCourseRequired() {
		return isCourseRequired;
	}

	public void setIsCourseRequired(Boolean isCourseRequired) {
		this.isCourseRequired = isCourseRequired;
	}

	public Boolean getIsNationalPlanUsed() {
        return this.isNationalPlanUsed;
    }

    public void setIsNationalPlanUsed(Boolean isNationalPlanUsed) {
        this.isNationalPlanUsed = isNationalPlanUsed;
    }

    public Boolean getIsNationalPlanRequired() {
        return this.isNationalPlanRequired;
    }

    public void setIsNationalPlanRequired(Boolean isNationalPlanRequired) {
        this.isNationalPlanRequired = isNationalPlanRequired;
    }

    public Boolean getIsTextbookUsed() {
        return this.isTextbookUsed;
    }

    public void setIsTextbookUsed(Boolean isTextbookUsed) {
        this.isTextbookUsed = isTextbookUsed;
    }

    public Boolean getIsTextbookRequired() {
        return this.isTextbookRequired;
    }

    public void setIsTextbookRequired(Boolean isTextbookRequired) {
        this.isTextbookRequired = isTextbookRequired;
    }

    public Boolean getIsPmphTextbookUsed() {
        return this.isPmphTextbookUsed;
    }

    public void setIsPmphTextbookUsed(Boolean isPmphTextbookUsed) {
        this.isPmphTextbookUsed = isPmphTextbookUsed;
    }

    public Boolean getIsPmphTextbookRequired() {
        return this.isPmphTextbookRequired;
    }

    public void setIsPmphTextbookRequired(Boolean isPmphTextbookRequired) {
        this.isPmphTextbookRequired = isPmphTextbookRequired;
    }

    public Boolean getIsResearchUsed() {
        return this.isResearchUsed;
    }

    public void setIsResearchUsed(Boolean isResearchUsed) {
        this.isResearchUsed = isResearchUsed;
    }

    public Boolean getIsResearchRequired() {
        return this.isResearchRequired;
    }

    public void setIsResearchRequired(Boolean isResearchRequired) {
        this.isResearchRequired = isResearchRequired;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsAllTextbookPublished() {
        return isAllTextbookPublished;
    }

    public void setIsAllTextbookPublished(Boolean isAllTextbookPublished) {
        this.isAllTextbookPublished = isAllTextbookPublished;
    }

    public Boolean getIsForceEnd() {
        return isForceEnd;
    }

    public void setIsForceEnd(Boolean isForceEnd) {
        this.isForceEnd = isForceEnd;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getFounderId() {
        return this.founderId;
    }

    public void setFounderId(Long founderId) {
        this.founderId = founderId;
    }

    public Timestamp getGmtUpdate() {
        return this.gmtUpdate;
    }

    public void setGmtUpdate(Timestamp gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Long getMenderId() {
        return this.menderId;
    }

    public void setMenderId(Long menderId) {
        this.menderId = menderId;
    }

    
	public Integer getProjectPermission() {
		return projectPermission;
	}

	public void setProjectPermission(Integer projectPermission) {
		this.projectPermission = projectPermission;
	}

	public Integer getPlanPermission() {
		return planPermission;
	}

	public void setPlanPermission(Integer planPermission) {
		this.planPermission = planPermission;
	}

	public Boolean getIsAchievementUsed() {
		return isAchievementUsed;
	}

	public void setIsAchievementUsed(Boolean isAchievementUsed) {
		this.isAchievementUsed = isAchievementUsed;
	}

	public Boolean getIsAchievementRequired() {
		return isAchievementRequired;
	}

	public void setIsAchievementRequired(Boolean isAchievementRequired) {
		this.isAchievementRequired = isAchievementRequired;
	}
	
	

	public Boolean getIsDigitalEditorOptional() {
		return isDigitalEditorOptional;
	}

	public void setIsDigitalEditorOptional(Boolean isDigitalEditorOptional) {
		this.isDigitalEditorOptional = isDigitalEditorOptional;
	}

	public Boolean getIsMonographUsed() {
		return isMonographUsed;
	}

	public Material setIsMonographUsed(Boolean isMonographUsed) {
		this.isMonographUsed = isMonographUsed;
		return this;
	}

	public Boolean getIsMonographRequired() {
		return isMonographRequired;
	}

	public Material setIsMonographRequired(Boolean isMonographRequired) {
		this.isMonographRequired = isMonographRequired;
		return this;
	}

	public Boolean getIsPublishRewardUsed() {
		return isPublishRewardUsed;
	}

	public Material setIsPublishRewardUsed(Boolean isPublishRewardUsed) {
		this.isPublishRewardUsed = isPublishRewardUsed;
		return this;
	}

	public Boolean getIsPublishRewardRequired() {
		return isPublishRewardRequired;
	}

	public Material setIsPublishRewardRequired(Boolean isPublishRewardRequired) {
		this.isPublishRewardRequired = isPublishRewardRequired;
		return this;
	}

	public Boolean getIsSciUsed() {
		return isSciUsed;
	}

	public Material setIsSciUsed(Boolean isSciUsed) {
		this.isSciUsed = isSciUsed;
		return this;
	}

	public Boolean getIsSciRequired() {
		return isSciRequired;
	}

	public Material setIsSciRequired(Boolean isSciRequired) {
		this.isSciRequired = isSciRequired;
		return this;
	}

	public Boolean getIsClinicalRewardUsed() {
		return isClinicalRewardUsed;
	}

	public Material setIsClinicalRewardUsed(Boolean isClinicalRewardUsed) {
		this.isClinicalRewardUsed = isClinicalRewardUsed;
		return this;
	}

	public Boolean getIsClinicalRewardRequired() {
		return isClinicalRewardRequired;
	}

	public Material setIsClinicalRewardRequired(Boolean isClinicalRewardRequired) {
		this.isClinicalRewardRequired = isClinicalRewardRequired;
		return this;
	}

	public Boolean getIsAcadeRewardUsed() {
		return isAcadeRewardUsed;
	}

	public Material setIsAcadeRewardUsed(Boolean isAcadeRewardUsed) {
		this.isAcadeRewardUsed = isAcadeRewardUsed;
		return this;
	}

	public Boolean getIsAcadeRewardRequired() {
		return isAcadeRewardRequired;
	}

	public Material setIsAcadeRewardRequired(Boolean isAcadeRewardRequired) {
		this.isAcadeRewardRequired = isAcadeRewardRequired;
		return this;
	}

	public Boolean getIsMoocDigitalUsed() {
		return isMoocDigitalUsed;
	}

	public void setIsMoocDigitalUsed(Boolean isMoocDigitalUsed) {
		this.isMoocDigitalUsed = isMoocDigitalUsed;
	}

	public Boolean getIsMoocDigitalRequired() {
		return isMoocDigitalRequired;
	}

	public void setIsMoocDigitalRequired(Boolean isMoocDigitalRequired) {
		this.isMoocDigitalRequired = isMoocDigitalRequired;
	}

	public Boolean getIsIntentionUsed() {
		return isIntentionUsed;
	}

	public void setIsIntentionUsed(Boolean isIntentionUsed) {
		this.isIntentionUsed = isIntentionUsed;
	}

	public Boolean getIsIntentionRequired() {
		return isIntentionRequired;
	}

	public void setIsIntentionRequired(Boolean isIntentionRequired) {
		this.isIntentionRequired = isIntentionRequired;
	}

	@Override
	public String toString() {
		return "Material [id=" + id + ", materialName=" + materialName
				+ ", materialRound=" + materialRound + ", materialType="
				+ materialType + ", deadline=" + deadline + ", actualDeadline="
				+ actualDeadline + ", ageDeadline=" + ageDeadline
				+ ", mailAddress=" + mailAddress + ", departmentId="
				+ departmentId + ", director=" + director + ", isMultiBooks="
				+ isMultiBooks + ", isMultiPosition=" + isMultiPosition
				+ ", isEduExpUsed=" + isEduExpUsed + ", isEduExpRequired="
				+ isEduExpRequired + ", isWorkExpUsed=" + isWorkExpUsed
				+ ", isWorkExpRequired=" + isWorkExpRequired
				+ ", isTeachExpUsed=" + isTeachExpUsed
				+ ", isTeachExpRequired=" + isTeachExpRequired
				+ ", isAcadeUsed=" + isAcadeUsed + ", isAcadeRequired="
				+ isAcadeRequired + ", isLastPositionUsed="
				+ isLastPositionUsed + ", isLastPositionRequired="
				+ isLastPositionRequired + ", isCourseUsed=" + isCourseUsed
				+ ", isCourseRequired=" + isCourseRequired
				+ ", isNationalPlanUsed=" + isNationalPlanUsed
				+ ", isNationalPlanRequired=" + isNationalPlanRequired
				+ ", isTextbookUsed=" + isTextbookUsed
				+ ", isTextbookRequired=" + isTextbookRequired
				+ ", isPmphTextbookUsed=" + isPmphTextbookUsed
				+ ", isPmphTextbookRequired=" + isPmphTextbookRequired
				+ ", isResearchUsed=" + isResearchUsed
				+ ", isResearchRequired=" + isResearchRequired
				+ ", isPublished=" + isPublished + ", isPublic=" + isPublic
				+ ", isAllTextbookPublished=" + isAllTextbookPublished
				+ ", isForceEnd=" + isForceEnd + ", isDeleted=" + isDeleted
				+ ", gmtCreate=" + gmtCreate + ", founderId=" + founderId
				+ ", gmtUpdate=" + gmtUpdate + ", menderId=" + menderId
				+ ", projectPermission=" + projectPermission
				+ ", planPermission=" + planPermission + ", isAchievementUsed="
				+ isAchievementUsed + ", isAchievementRequired="
				+ isAchievementRequired + ", isDigitalEditorOptional="
				+ isDigitalEditorOptional + ", isMonographUsed="
				+ isMonographUsed + ", isMonographRequired="
				+ isMonographRequired + ", isPublishRewardUsed="
				+ isPublishRewardUsed + ", isPublishRewardRequired="
				+ isPublishRewardRequired + ", isSciUsed=" + isSciUsed
				+ ", isSciRequired=" + isSciRequired
				+ ", isClinicalRewardUsed=" + isClinicalRewardUsed
				+ ", isClinicalRewardRequired=" + isClinicalRewardRequired
				+ ", isAcadeRewardUsed=" + isAcadeRewardUsed
				+ ", isAcadeRewardRequired=" + isAcadeRewardRequired
				+ ", isMoocDigitalUsed=" + isMoocDigitalUsed
				+ ", isMoocDigitalRequired=" + isMoocDigitalRequired
				+ ", isIntentionUsed=" + isIntentionUsed
				+ ", isIntentionRequired=" + isIntentionRequired + "]";
	}
	
}