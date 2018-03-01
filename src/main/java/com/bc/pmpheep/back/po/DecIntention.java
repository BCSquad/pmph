package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 编写内容意向表实体类
 * @author tyc
 * 2018年02月28日下午17:05
 */
@SuppressWarnings("serial")
@Alias("DecIntention")
public class DecIntention implements Serializable {
	// 主键
	private Long id;
	// 申报id
	private Long declarationId;
	// 编写内容意向
	private String content;
	
	// 构造器
	public DecIntention(){
		
	}
	
	public DecIntention(Long declarationId, String content){
		super();
		this.declarationId = declarationId;
		this.content = content;
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