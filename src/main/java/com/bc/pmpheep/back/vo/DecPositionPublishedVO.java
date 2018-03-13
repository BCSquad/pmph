package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 已公布作家申报职位表实体类VO
 * 
 * @author tyc 2018年3月12日 17:02
 */
@SuppressWarnings("serial")
@Alias("DecPositionPublishedVO")
public class DecPositionPublishedVO implements Serializable {
    // 主键
    private Long      id;
    // 公布人id
    private Long      publisherId;
    // 申报表
    private Long      declarationId;
    // 是否进入预选名单
    private Boolean   isOnList;
    // 遴选职务
    private Integer   chosenPosition;
    // 排位
    private Integer   rank;
	// 显示遴选职务
    private String showChosenPosition;

    public DecPositionPublishedVO() {

    }

    public DecPositionPublishedVO(Long declarationId) {
        this.declarationId = declarationId;
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

    public String getShowChosenPosition() {
		return showChosenPosition;
	}

	public void setShowChosenPosition(String showChosenPosition) {
		this.showChosenPosition = showChosenPosition;
	}

	@Override
	public String toString() {
		return "DecPositionPublishedVO [id=" + id + ", publisherId="
				+ publisherId + ", declarationId=" + declarationId
				+ ", isOnList=" + isOnList + ", chosenPosition="
				+ chosenPosition + ", rank=" + rank + ", showChosenPosition="
				+ showChosenPosition + "]";
	}
}
