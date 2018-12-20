package com.bc.pmpheep.back.po;

import com.mchange.lang.LongUtils;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@SuppressWarnings("serial")
@Alias("Activity")
public class Activity implements Serializable {

    // 活动id
    private Long id;
    // 活动名称
    private String activityName;
    //活动活动日期
    private Timestamp activityDate;
    //是否置顶
    private Boolean isSetTop;
    //最后置顶时间
    private Timestamp gmtSetTop;
    //状态
    private Integer status;
    //创建人id
    private Long founderId;
    //创建时间
    private Timestamp gmtCreate;
    //x修改时间
    private Timestamp gmtUpdate;
    //教材id
    private Long materialId;
    //关联信息快报id
    private Long infoExpressCmsId;
    //关联活动简介
    private String activityDescCmsId;
    //封面的MongoDB的id
    private String cover;

    public Activity(String activityName, Timestamp activityDate, Boolean isSetTop, Integer status, Long materialId, Long infoExpressCmsId) {
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.isSetTop = isSetTop;
        this.status = status;
        this.materialId = materialId;
        this.infoExpressCmsId = infoExpressCmsId;
    }

    public Activity(String activityName, Timestamp activityDate, Boolean isSetTop, Integer status) {
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.isSetTop = isSetTop;
        this.status = status;
    }

    public Activity(Long id, String activityName, Timestamp activityDate, Boolean isSetTop, Timestamp gmtSetTop, Integer status, Long founderId, Timestamp gmtCreate, Timestamp gmtUpdate, Long materialId, Long infoExpressCmsId, String activityDescCmsId, String cover, Boolean isDelected) {
        this.id = id;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.isSetTop = isSetTop;
        this.gmtSetTop = gmtSetTop;
        this.status = status;
        this.founderId = founderId;
        this.gmtCreate = gmtCreate;
        this.gmtUpdate = gmtUpdate;
        this.materialId = materialId;
        this.infoExpressCmsId = infoExpressCmsId;
        this.activityDescCmsId = activityDescCmsId;
        this.cover = cover;
        this.isDelected = isDelected;
    }

    //是否已逻辑删除
    private Boolean isDelected;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Timestamp getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Timestamp activityDate) {
        this.activityDate = activityDate;
    }




    public Timestamp getGmtSetTop() {
        return gmtSetTop;
    }

    public void setGmtSetTop(Timestamp gmtSetTop) {
        this.gmtSetTop = gmtSetTop;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getFounderId() {
        return founderId;
    }

    public void setFounderId(Long founderId) {
        this.founderId = founderId;
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

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId =Long.parseLong(materialId);
    }

    public Long getInfoExpressCmsId() {
        return infoExpressCmsId;
    }


    public String getActivityDescCmsId() {
        return activityDescCmsId;
    }

    public void setActivityDescCmsId(String activityDescCmsId) {
        this.activityDescCmsId = activityDescCmsId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Boolean getIsSetTop() {
        return isSetTop;
    }

    public void setIsSetTop(Boolean setTop) {
        isSetTop = setTop;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public void setInfoExpressCmsId(Long infoExpressCmsId) {
        this.infoExpressCmsId = infoExpressCmsId;
    }

    public Boolean geIstDelected() {
        return isDelected;
    }

    public void setIsDelected(Boolean delected) {
        isDelected = delected;
    }


    public Activity() {
    }

}


