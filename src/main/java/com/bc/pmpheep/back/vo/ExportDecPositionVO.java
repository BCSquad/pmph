package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.type.Alias;

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
   	// 作家信息
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