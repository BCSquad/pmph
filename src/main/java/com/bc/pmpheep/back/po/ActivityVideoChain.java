package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@SuppressWarnings("serial")
@Alias("ActivityVideoChain")
public class ActivityVideoChain implements Serializable {
    //活动视频关联id
    private Long id;
    //活动id
    private Long activityId;
    //视频id
    private Long activityVideoId;

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

    public Long getActivityVideoId() {
        return activityVideoId;
    }

    public void setActivityVideoId(Long activityVideoId) {
        this.activityVideoId = activityVideoId;
    }
}
