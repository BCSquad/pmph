package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
@Alias("ActivityVideoChain")
public class ActivityVideoChain implements Serializable {
    //活动视频关联id
    private Long id;
    //活动id
    private Long activityId;
    //视频id
    private Long activityVideoId;
    private Long sort;

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

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

    public ActivityVideoChain(Long activityId, Long activityVideoId) {
        this.activityId = activityId;
        this.activityVideoId = activityVideoId;
    }

    public ActivityVideoChain(Long id, Long activityId, Long activityVideoId, Long sort) {
        this.id = id;
        this.activityId = activityId;
        this.activityVideoId = activityVideoId;
        this.sort = sort;
    }

    public ActivityVideoChain() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityVideoChain that = (ActivityVideoChain) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(activityId, that.activityId) &&
                Objects.equals(activityVideoId, that.activityVideoId) &&
                Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityId, activityVideoId, sort);
    }
}
