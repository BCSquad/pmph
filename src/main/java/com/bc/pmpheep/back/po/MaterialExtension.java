package com.bc.pmpheep.back.po;


import org.apache.ibatis.type.Alias;

/**
 * MaterialExtension  教材信息扩展项表（一对多）    实体类 
 * @author mryang 
 *
 */
@SuppressWarnings("serial")
@Alias("MaterialExtension")
public class MaterialExtension implements java.io.Serializable {
	//主键
	private Long id;
	//教材id
	private Long materialId;
	//扩展项名称
	private String extensionName;
	//是否必填
	private Boolean isRequired;

	// Constructors

	/** default constructor */
	public MaterialExtension() {
	}
	

	public MaterialExtension(Long id) {
		super();
		this.id = id;
	}


	/** full constructor */
	public MaterialExtension(Long materialId, String extensionName,
			Boolean isRequired) {
		this.materialId = materialId;
		this.extensionName = extensionName;
		this.isRequired = isRequired;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterialId() {
		return this.materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getExtensionName() {
		return this.extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public Boolean getIsRequired() {
		return this.isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", materialId:" + materialId + ", extensionName:"
				+ extensionName + ", isRequired:" + isRequired + "}";
	}
	
	

}