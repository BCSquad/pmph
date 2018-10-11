package com.bc.pmpheep.back.po;


import org.apache.ibatis.type.Alias;

/**
 * 
 * @introduction  作家兼职学术表 实体
 *
 * @author Mryang
 *
 * @createDate 2017年9月21日 下午6:09:42
 *
 */
@SuppressWarnings("serial")
@Alias("DecAcade")
public class DecAcade implements java.io.Serializable {

	//主键
	private Long id;
	//申报表id
	private Long declarationId;
	//兼职学术组织
	private String orgName;
	//级别
	private Integer rank;
	private String rankName;
	//职务
	private String position;
	//备注
	private String note;
	//显示顺序
	private Integer sort;
	//个人资料id
	private String perId;
	//临床决策申报id
	private String expertationId;

	// Constructors

	/** default constructor */
	public DecAcade() {
		
	}

	public DecAcade(Long id) {
		this.id=id;
	}

	/** full constructor */
	public DecAcade(Long declarationId, String orgName, Integer rank,
			String position, String note, Integer sort) {
		this.declarationId = declarationId;
		this.orgName = orgName;
		this.rank = rank;
		this.position = position;
		this.note = note;
		this.sort = sort;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeclarationId() {
		return this.declarationId;
	}

	public void setDeclarationId(Long declarationId) {
		this.declarationId = declarationId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", declarationId:" + declarationId + ", orgName:"
				+ orgName + ", rank:" + rank + ", position:" + position
				+ ", note:" + note + ", sort:" + sort + "}";
	}


	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getExpertationId() {
		return expertationId;
	}

	public void setExpertationId(String expertationId) {
		this.expertationId = expertationId;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
}