package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@SuppressWarnings("serial")
@Alias("ActivitySourceChain")
public class ActivitySourceChain implements Serializable {
    //活动资源关联id
    private Long id;
    //活动id
    private Long activityId;
    //资源id
    private Long activitySourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivitySourceId() {
        return activitySourceId;
    }

    public void setActivitySourceId(Long activitySourceId) {
        this.activitySourceId = activitySourceId;
    }


}