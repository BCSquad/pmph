package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Date;

import org.apache.ibatis.type.Alias;

/**
 * 出版行业获奖情况表实体类
 * @author tyc
 * 2018年1月16日10:01
 */
@SuppressWarnings("serial")
@Alias("DecPublishReward")
public class DecPublishReward implements Serializable {

	// 主键
    private Long id;
    // 申报表id
    private Long declarationId;
    // 奖项名称
    private String rewardName;
    // 评奖单位
    private String awardUnit;
    // 获奖时间
    private Date rewardDate;
    // 备注
    private String note;
    // 显示顺序
    private Integer sort;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDeclarationId() {
		return declarationId;
	}
	public void setDeclarationId(Long declarationId) {
		this.declarationId = declarationId;
	}
	public String getRewardName() {
		return rewardName;
	}
	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	public String getAwardUnit() {
		return awardUnit;
	}
	public void setAwardUnit(String awardUnit) {
		this.awardUnit = awardUnit;
	}
	public Date getRewardDate() {
		return rewardDate;
	}
	public void setRewardDate(Date rewardDate) {
		this.rewardDate = rewardDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Override
	public String toString() {
		return "DecPublishReward [id=" + id + ", declarationId="
				+ declarationId + ", rewardName=" + rewardName + ", awardUnit="
				+ awardUnit + ", rewardDate=" + rewardDate + ", note=" + note
				+ ", sort=" + sort + "]";
	}
    
}
