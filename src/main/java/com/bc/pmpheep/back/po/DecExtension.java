package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年9月30日 下午5:14:13
 *
 **/
@SuppressWarnings("serial")
@Alias("DecExtension")
public class DecExtension implements java.io.Serializable {
	
	//主键
	private Long id;
	//教材扩展项id
	private Long extension_id;
	//申报表id
	private Long declarationId;
	//扩展值
	private String content;
	
	public DecExtension() {
		super();
	}

	public DecExtension(Long id, Long extension_id, Long declarationId,
			String content) {
		super();
		this.id = id;
		this.extension_id = extension_id;
		this.declarationId = declarationId;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExtension_id() {
		return extension_id;
	}

	public void setExtension_id(Long extension_id) {
		this.extension_id = extension_id;
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
		return "{id:" + id + ", extension_id:" + extension_id
				+ ", declarationId:" + declarationId + ", content:" + content
				+ "}";
	}
	
	
	
}
