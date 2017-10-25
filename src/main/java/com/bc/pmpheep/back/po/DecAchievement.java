package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 个人成就
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:19:28
 *
 */
@SuppressWarnings("serial")
@Alias("DecAchievement")
public class DecAchievement implements java.io.Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 申报id
	 */
	private Long declarationId;
	/**
	 * 成就
	 */
	private String achievement;

	public DecAchievement() {
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

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", declarationId:" + declarationId
				+ ", achievement:" + achievement + "}";
	}

	

}