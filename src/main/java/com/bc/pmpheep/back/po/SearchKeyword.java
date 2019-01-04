package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * 
 * 功能描述：敏感词实体类
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018年1月29日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("SearchKeyword")
public class SearchKeyword implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 敏感词
	 */
	private String word;
	/**
	 * 排序码
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 是否禁用
	 */
	private Boolean isDisabled;
	/**
	 * 是否逻辑删除
	 */
	private Boolean isDeleted;
	/**
	 * 创建时间
	 */
	private Timestamp gmtCreate;
	/**
	 * 修改时间
	 */
	private Timestamp gmtUpdate;

	public SearchKeyword() {
		super();
	}

	public SearchKeyword(String word, Integer sort, String note, Boolean isDisabled, Boolean isDeleted, Timestamp gmtCreate,
                         Timestamp gmtUpdate) {
		super();
		this.word = word;
		this.sort = sort;
		this.note = note;
		this.isDisabled = isDisabled;
		this.isDeleted = isDeleted;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Timestamp gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

}
