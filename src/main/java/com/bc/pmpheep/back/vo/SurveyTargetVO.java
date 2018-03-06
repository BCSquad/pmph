package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <pre>
 * 功能描述：发起问卷VO
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-1-4
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Alias("SurveyTargetVO")
public class SurveyTargetVO implements Serializable {

    //
    private static final long serialVersionUID = 1585071043623160665L;
    /**
     * 问卷标题
     */
    private String            title;
    /**
     * 问卷id
     */
    private Long              surveyId;
    /**
     * 问卷调查学校
     */
    private List<Long>        orgIds;
    /**
     * 问卷开始时间
     */
    private String            startTime;
    /**
     * 问卷结束时间
     */
    private String            endTime;
    /**
     * 发送类型 (1:发起问卷，2：补发消息)
     */
    private Integer           sendType;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the surveyId
     */
    public Long getSurveyId() {
        return surveyId;
    }

    /**
     * @param surveyId the surveyId to set
     */
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * @return the orgIds
     */
    public List<Long> getOrgIds() {
        return orgIds;
    }

    /**
     * @param orgIds the orgIds to set
     */
    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
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

    /**
     * @return the sendType
     */
    public Integer getSendType() {
        return sendType;
    }

    /**
     * @param sendType the sendType to set
     */
    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

}
