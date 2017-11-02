package com.bc.pmpheep.back.po;



import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家精品课程建设情况表 实体类<p>
 * <p>Description:作家精品课程建设情况信息<p>
 * @author lyc
 * @date 2017年9月22日 上午10:04:07
 */
@SuppressWarnings("serial")
@Alias("DecCourseConstruction")
public class DecCourseConstruction implements java.io.Serializable {

	//主键
	private Long id;
	//申报表id
	private Long declarationId;
	//课程名称
	private String courseName;
	//课程全年课时
	private String classHour;
	//职务
	private Integer type;
	//备注
	private String note;
	//显示顺序
	private Integer sort;

	// 构造器

	/** default constructor */
	public DecCourseConstruction() {
	}

	

	public DecCourseConstruction(Long id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public DecCourseConstruction(Long declarationId, String courseName,
			String classHour, Integer type, String note, Integer sort) {
		this.declarationId = declarationId;
		this.courseName = courseName;
		this.classHour = classHour;
		this.type = type;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getClassHour() {
		return classHour;
	}

	public void setClassHour(String classHour) {
		this.classHour = classHour;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
				+ ", courseName:" + courseName + ", classHour:" + classHour
				+ ", type:" + type + ", note:" + note + ", sort:" + sort + "}";
	}
    	
}