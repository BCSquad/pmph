package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.sql.Date;

import org.apache.ibatis.type.Alias;

/**
 * 学术荣誉授予情况表实体类
 * @author tyc
 * 2018年1月16日15:44
 */
@SuppressWarnings("serial")
@Alias("DecAcadeReward")
public class DecAcadeReward implements Serializable {
	// 主键
    private Long id;
    // 申报表id
    private Long declarationId;
    // 荣誉名称
    private String rewardName;
    // 荣誉级别（0=无/1=国际/2=国家/3=省部/4=市）
    private Integer awardUnit;
    // 授予时间
    private Date rewardDate;
    // 备注
    private String note;
    // 显示顺序
    private Integer sort;
	//个人资料id
	private String perId;
    
    public DecAcadeReward() {
    	
    }
    
	public DecAcadeReward(Long declarationId, String rewardName, Integer awardUnit, 
			Date rewardDate, String note, Integer sort) {
		super();
		this.declarationId = declarationId;
		this.rewardName = rewardName;
		this.awardUnit = awardUnit;
		this.rewardDate = rewardDate;
		this.note = note;
		this.sort = sort;
	}
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
	public Integer getAwardUnit() {
		return awardUnit;
	}
	public void setAwardUnit(Integer awardUnit) {
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
		return "DecAcadeReward [id=" + id + ", declarationId=" + declarationId
				+ ", rewardName=" + rewardName + ", awardUnit=" + awardUnit
				+ ", rewardDate=" + rewardDate + ", note=" + note + ", sort="
				+ sort + "]";
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}
}
