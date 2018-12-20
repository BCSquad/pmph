package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
@SuppressWarnings("serial")
@Alias("ActivityVO")
public class ActivityVO implements Serializable {
    private Long id;
    private String activityName;
    private Date activityDate;
    private Integer isSetTop;
    private Integer status;
    private String realname;
    private Date gmtCreate;
    private Integer   count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

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

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public Integer getIsSetTop() {
        return isSetTop;
    }

    public void setIsSetTop(Integer isSetTop) {
        this.isSetTop = isSetTop;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
