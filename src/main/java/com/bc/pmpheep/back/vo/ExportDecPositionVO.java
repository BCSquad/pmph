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
	
	private List<WriterBO> writers;
	
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
	public List<WriterBO> getWriters() {
		return writers;
	}
	public void setWriters(List<WriterBO> writers) {
		this.writers = writers;
	}
	   
}