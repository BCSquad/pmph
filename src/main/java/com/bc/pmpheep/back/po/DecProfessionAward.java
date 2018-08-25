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
@Alias("DecProfessionAward")
public class DecProfessionAward implements java.io.Serializable {

	//主键
	private Long id;
	//备注
	private String note;
	//个人资料id
	private String perId;
	//临床决策申报id
	private String expertationId;
	//题目
	private String title;

	//级别（国家、省、市、单位）
	private String rank;

	// Constructors

	/** default constructor */
	public DecProfessionAward() {

	}

	public DecProfessionAward(Long id) {
		this.id=id;
	}

	public DecProfessionAward(String note) {
		this.note = note;
	}


	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "DecProfessionAward{" +
				"id=" + id +
				", note='" + note + '\'' +
				", perId='" + perId + '\'' +
				", expertationId='" + expertationId + '\'' +
				", title='" + title + '\'' +
				", rank='" + rank + '\'' +
				'}';
	}
}