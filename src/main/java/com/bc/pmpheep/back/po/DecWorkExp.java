package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家工作经历表 实体类<p>
 * <p>Description:作家工作经历信息<p>
 * @author lyc
 * @date 2017年9月22日 上午10:29:26
 */
@SuppressWarnings("serial")
@Alias("DecWorkExp")
public class DecWorkExp implements java.io.Serializable {

	// 主键
	private Long id;
	// 申报表id
	private Long declarationId;
	// 工作单位
	private String orgName;
	// 职位
	private String position;
	// 备注
	private String note;
	// 起始时间
	private String dateBegin;
	// 终止时间
	private String dateEnd;
	// 显示顺序
	private Integer sort;
	//个人资料id
	private String perId;
	//临床决策申报id
	private Long expertationId;

	// 构造器

	/** default constructor */
	public DecWorkExp() {
	}

	

	public DecWorkExp(Long id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public DecWorkExp(Long declarationId, String orgName, String position,
			String note, String dateBegin, String dateEnd, Integer sort) {
		this.declarationId = declarationId;
		this.orgName = orgName;
		this.position = position;
		this.note = note;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.sort = sort;
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



	public String getOrgName() {
		return orgName;
	}



	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public String getDateBegin() {
		return dateBegin;
	}



	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}



	public String getDateEnd() {
		return dateEnd;
	}



	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}



	public Integer getSort() {
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
	}



	@Override
	public String toString() {
		return " {id:" + id + ", declarationId:" + declarationId + ", orgName:"
				+ orgName + ", position:" + position + ", note:" + note
				+ ", dateBegin:" + dateBegin + ", dateEnd:" + dateEnd
				+ ", sort:" + sort + "}";
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public Long getExpertationId() {
		return expertationId;
	}

	public void setExpertationId(Long expertationId) {
		this.expertationId = expertationId;
	}
}