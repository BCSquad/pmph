package com.bc.pmpheep.back.vo;

import org.apache.ibatis.type.Alias;

/**
 *@author tyc 
 *@CreateDate 2017年12月12日上午10:41:13
 *
 **/
@SuppressWarnings("serial")
@Alias("DecExtensionVO")
public class DecExtensionVO implements java.io.Serializable {
	
	//主键
	private Long id;
	//教材扩展项id
	private Long extensionId;
	//申报表id
	private Long declarationId;
	//扩展值
	private String content;
	//扩展项名称
	private String extensionName;
	
	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public DecExtensionVO() {
		super();
	}
	
	public DecExtensionVO(Long id) {
		super();
		this.id = id;
	}


	public DecExtensionVO(Long extensionId, Long declarationId,
			String content) {
		super();
		this.extensionId = extensionId;
		this.declarationId = declarationId;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(Long extensionId) {
		this.extensionId = extensionId;
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
		return "DecExtensionVO [id=" + id + ", extensionId=" + extensionId
				+ ", declarationId=" + declarationId + ", content=" + content
				+ ", extensionName=" + extensionName + "]";
	}
	
	
}
