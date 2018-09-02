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
	private Long extensionId;
	//申报表id
	private Long declarationId;
	//扩展值
	private String content;

	private Long expertationId;

	private String extension_name;
	
	public DecExtension() {
		super();
	}
	
	public DecExtension(Long id) {
		super();
		this.id = id;
	}


	public DecExtension(Long extensionId, Long declarationId,
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
		return "DecExtension{" +
				"id=" + id +
				", extensionId=" + extensionId +
				", declarationId=" + declarationId +
				", content='" + content + '\'' +
				", expertationId=" + expertationId +
				'}';
	}

	public Long getExpertationId() {
		return expertationId;
	}

	public void setExpertationId(Long expertationId) {
		this.expertationId = expertationId;
	}

	public String getExtension_name() {
		return extension_name;
	}

	public void setExtension_name(String extension_name) {
		this.extension_name = extension_name;
	}
}
