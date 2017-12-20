package com.bc.pmpheep.back.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * 
 * 功能描述：选题申报编者情况表实体
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月19日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Alias("TopicWriter")
public class TopicWriter implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 选题id
	 */
	private Long topicId;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 性别 0=男/1=女
	 */
	private Integer sex;
	/**
	 * 年龄
	 */
	private Integer price;
	/**
	 * 行政单位
	 */
	private String position;
	/**
	 * 工作单位
	 */
	private String workplace;

	public TopicWriter() {
		super();
	}

	public TopicWriter(Long topicId, String realname, Integer sex, Integer price, String position, String workplace) {
		super();
		this.topicId = topicId;
		this.realname = realname;
		this.sex = sex;
		this.price = price;
		this.position = position;
		this.workplace = workplace;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

}
