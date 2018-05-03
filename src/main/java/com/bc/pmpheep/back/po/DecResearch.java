package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家科研情况表 实体类<p>
 * <p>Description:作家科研情况信息<p>
 * @author lyc
 * @date 2017年9月22日 上午10:22:02
 */
@SuppressWarnings("serial")
@Alias("DecResearch")
public class DecResearch implements java.io.Serializable {

	// 主键
	private Long id;
	// 申报表id
	private Long declarationId;
	// 课题名称
	private String researchName;
	// 审批单位
	private String approvalUnit;
	// 获奖情况
	private String award;
	// 备注
	private String note;
	// 显示顺序
	private Integer sort;
	//个人资料id
	private String perId;

	// 构造器

	/** default constructor */
	public DecResearch() {
	}

	

	public DecResearch(Long id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public DecResearch(Long declarationId, String researchName,
			String approvalUnit, String award, String note, Integer sort) {
		this.declarationId = declarationId;
		this.researchName = researchName;
		this.approvalUnit = approvalUnit;
		this.award = award;
		this.note = note;
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



	public String getResearchName() {
		return researchName;
	}



	public void setResearchName(String researchName) {
		this.researchName = researchName;
	}



	public String getApprovalUnit() {
		return approvalUnit;
	}



	public void setApprovalUnit(String approvalUnit) {
		this.approvalUnit = approvalUnit;
	}



	public String getAward() {
		return award;
	}



	public void setAward(String award) {
		this.award = award;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public Integer getSort() {
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
	}



	@Override
	public String toString() {
		return " {id:" + id + ", declarationId:" + declarationId
				+ ", researchName:" + researchName + ", approvalUnit:"
				+ approvalUnit + ", award:" + award + ", note:" + note
				+ ", sort:" + sort + "}";
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}
}