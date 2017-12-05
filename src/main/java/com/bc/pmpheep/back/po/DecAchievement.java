package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 个人成就
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月24日 下午5:19:28
 * 修改：tyc
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
	private String content;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "DecAchievement [id=" + id + ", declarationId=" + declarationId
				+ ", content=" + content + "]";
	}
	
}