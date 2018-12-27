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
@Alias("DecArticlePublished")
public class DecArticlePublished implements java.io.Serializable {

	//主键
	private Long id;
	//备注
	private String note;
	//个人资料id
	private String perId;
	//临床决策申报id
	private Long expertationId;
	//题目
	private String title;
	//期刊名称
	private String periodicalTitle;
	//期刊级别（SCI或国内核心期刊）
	private String periodicalLevel;

	// Constructors

	/** default constructor */
	public DecArticlePublished() {

	}

	public DecArticlePublished(Long id) {
		this.id=id;
	}

	public DecArticlePublished(String note) {
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

	public Long getExpertationId() {
		return expertationId;
	}

	public void setExpertationId(Long expertationId) {
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

	public String getPeriodicalTitle() {
		return periodicalTitle;
	}

	public void setPeriodicalTitle(String periodicalTitle) {
		this.periodicalTitle = periodicalTitle;
	}

	public String getPeriodicalLevel() {
		return periodicalLevel;
	}

	public void setPeriodicalLevel(String periodicalLevel) {
		this.periodicalLevel = periodicalLevel;
	}

	@Override
	public String toString() {
		return "DecArticlePublished{" +
				"id=" + id +
				", note='" + note + '\'' +
				", perId='" + perId + '\'' +
				", expertationId='" + expertationId + '\'' +
				", title='" + title + '\'' +
				", periodicalTitle='" + periodicalTitle + '\'' +
				", periodicalLevel='" + periodicalLevel + '\'' +
				'}';
	}
}