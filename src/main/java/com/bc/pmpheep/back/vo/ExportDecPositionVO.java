package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.bc.pmpheep.back.bo.WriterBO;

/**
 * 
 * @author 主编/副主编
 *
 */
@SuppressWarnings("serial")
@Alias("ExportDecPositionVO")
public class ExportDecPositionVO implements Serializable{
	
	//序号
	private Integer sort;
   	// 书籍名称
	private String textbookName;
   	// 书籍轮次
	private Integer textbookRound;
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
	private List<WriterVO> writers;
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getTextbookName() {
		return textbookName;
	}
	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}
	public Integer getTextbookRound() {
		return textbookRound;
	}
	public void setTextbookRound(Integer textbookRound) {
		this.textbookRound = textbookRound;
	}
	public List<WriterVO> getWriters() {
		return writers;
	}
	public void setWriters(List<WriterVO> writers) {
		this.writers = writers;
	}
	   
}