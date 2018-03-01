package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 参加人卫慕课、数字教材编写情况实体类
 * @author tyc
 * 2018年02月28日下午17:01
 */
@SuppressWarnings("serial")
@Alias("DecMoocDigital")
public class DecMoocDigital implements Serializable {
	// 主键
	private Long id;
	// 申报id
	private Long declarationId;
	// 编写情况内容
	private String content;
	
	// 构造器
	public DecMoocDigital(){
		
	}
	
	public DecMoocDigital(Long declarationId, String content){
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