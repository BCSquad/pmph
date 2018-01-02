package com.bc.pmpheep.back.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * @author mr
 *
 */
@SuppressWarnings("serial")
@Alias("WriterVO")
public class WriterVO implements Serializable{
	// 真实姓名
    private String realname;
    // 申报单位
    private String chosenOrgName;
    // 职务
    private Integer chosenPosition;
    // 排位
    private Integer rank;
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getChosenOrgName() {
		return chosenOrgName;
	}
	public void setChosenOrgName(String chosenOrgName) {
		this.chosenOrgName = chosenOrgName;
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
    
}
